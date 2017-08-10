package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactAddToGroup extends TestBase{


    @Test
    public void testContactAddToGroup() {
        Contacts beforeContacts = app.db().contacts();
        if (beforeContacts.size() == 0) {
            app.goTo().contactPage();
            app.contact().create(new ContactData().withFirstName("Andrey").withLastName("Zakrenichnyy").withAddress("Nikolaevich").withHomePhone("1234567"), true);
            beforeContacts = app.db().contacts();
        }
        Groups beforeGroups = app.db().groups();
        if (beforeGroups.isEmpty()) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("nTest01").withFooter("fTest01").withHeader("hTest01"));
            beforeGroups = app.db().groups();
        }
        ContactData modifiedContact = beforeContacts.iterator().next();
        GroupData groupToAdd = null;
        Groups allGroupsContactAdded = modifiedContact.getGroups();

        // проверяем, состоит ли контакт в какой-нибудь группе
        // если Да, то ищем свободную группу
        if (allGroupsContactAdded.size() == 0) {
            groupToAdd = beforeGroups.iterator().next();
        } else {
            for (GroupData currentGroup : beforeGroups) {
                if(!allGroupsContactAdded.contains(currentGroup)) {
                    groupToAdd = currentGroup;
                    break;
                }
            }
        }
        // создаем новую группу,если контакт присутствует во всех существующих группах
        if (groupToAdd == null) {
            GroupData newGroup = new GroupData().withName("nTest02").withFooter("fTest02").withHeader("hTest02");
            app.goTo().groupPage();
            app.group().create(newGroup);
            groupToAdd = newGroup;
        }

        app.goTo().homePage();
        app.contact().selectContactById(modifiedContact.getId());
        app.contact().selectGroupToAdd(groupToAdd);
        app.contact().submitContactAddToGroup();

        Contacts modContacts = app.db().getContactById(modifiedContact.getId());
        if (modContacts.size()==1) {
            Groups groupsAfterAdded = (modContacts.iterator().next()).getGroups();
            Boolean groupIsAdded = false;
            for (GroupData group : groupsAfterAdded) {
                if (group.getName().equals(groupToAdd.getName())){
                    groupIsAdded = true;
                    break;
                }
            }
            Assert.assertTrue(groupIsAdded, "Контакт "+ modifiedContact +" не был добален в группу <"+ groupToAdd.getName() +">");
        } else if (modContacts.size()>1) {
            Assert.assertTrue(false, "В базе найдено более одного контакта с id"+ modifiedContact.getId());
        } else if(modContacts.size()==0){
            Assert.assertTrue(false, "В базе не найдено ни одного контакта с id"+ modifiedContact.getId());
        }

    }

}
