package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

public class ContactDeleteFromGroup extends TestBase{

    @Test
    public  void testContactDeleteFromGroup() {
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
        Groups listGroupsOfContact = modifiedContact.getGroups();
        GroupData group = null;
        if (listGroupsOfContact.size() == 0) {
            group = beforeGroups.iterator().next();
            app.goTo().homePage();
            app.contact().selectContactById(modifiedContact.getId());
            app.contact().selectGroupToAdd(group);
            app.contact().submitContactAddToGroup();
            Contacts contactsAfterAddToGroup = app.db().getContactById(modifiedContact.getId());
            listGroupsOfContact = contactsAfterAddToGroup.iterator().next().getGroups();
            System.out.println("Контакт добавлен в группу = " + group.getName());
        }

        GroupData groupToRemove = listGroupsOfContact.iterator().next();
        app.goTo().homePage();
        app.contact().filterContactsByGroup(groupToRemove);
        app.contact().selectContactById(modifiedContact.getId());
        app.contact().deleteContactFromGroup();
        System.out.println("Контакт удален из группы = " + groupToRemove.getName());

        Contacts contactsAfterDeleteFromGroup = app.db().getContactById(modifiedContact.getId());
        if (contactsAfterDeleteFromGroup.size()==1) {
            Groups listGroupsOfContactAfterDeleteGroup = (contactsAfterDeleteFromGroup.iterator().next()).getGroups();
            Boolean groupIsRemoved = true;
            for (GroupData gr: listGroupsOfContactAfterDeleteGroup){
                if (gr.getName().equals(groupToRemove.getName())){
                    groupIsRemoved = false;
                    break;
                }
            }
            Assert.assertTrue(groupIsRemoved, "Контакт "+ modifiedContact +" не был удален из группы"+ groupToRemove.getName());
        } else if (contactsAfterDeleteFromGroup.size()>1) {
            Assert.assertTrue(false, "В базе найдено более одного контакта с id"+ modifiedContact.getId());
        } else if(contactsAfterDeleteFromGroup.size()==0){
            Assert.assertTrue(false, "В базе не найдено ни одного контакта с id"+ modifiedContact.getId());
        }

    }

}

