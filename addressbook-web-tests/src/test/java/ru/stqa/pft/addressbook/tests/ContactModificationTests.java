package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().homePage();
        if (app.contact().list().size()==0){
            app.goTo().contactPage();
            app.contact().create(new ContactData().withFirstName("Andrey").withLastName("Zakrenichnyy").withMiddleName("Nikolaevich").withEmail("zik2004@mail.ru").withGroup("test01").withPhone("1234567"), true);
        }
    }


    @Test
    public void testContactModification(){
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("No_name").withLastName("No_name").withMiddleName("No_name").withEmail("No_email").withPhone("No_phone").withAddress("No_adress");
        app.contact().modify(contact);
        app.goTo().homePage();
        Contacts after = app.contact().all();
        assertThat(before.size(), equalTo(after.size()));
        assertThat(before, equalTo(before. withoutAdded(modifiedContact).withAdded(contact)));
    }


}
