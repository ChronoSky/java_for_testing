package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions(){
        if (app.db().contacts().size()==0){
            if (app.db().groups().size()==0){
                app.goTo().groupPage();
                app.group().create(new GroupData().withName("test_01").withHeader("test_01").withFooter("test_01"));
            }
            Groups groups = app.db().groups();
            app.goTo().contactPage();
            app.contact().create(new ContactData().withFirstName("Andrey").withLastName("Zakrenichnyy").withMiddleName("Nikolaevich").withEmail("zik2004@mail.ru")
                    .inGroup(groups.iterator().next()).withHomePhone("1234567").withMobilePhone("89261234567").withWorkPhone("1234"), true);
        }
    }


    @Test
    public void testContactModification(){
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("No_name").withLastName("No_name").withMiddleName("No_name")
                .withEmail("No_email").withHomePhone("No_homePhone").withMobilePhone("No_mobilePhone").withWorkPhone("No_workPhone").withAddress("No_adress");
        app.contact().modify(contact);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before. withoutAdded(modifiedContact).withAdded(contact)));
    }


}
