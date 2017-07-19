package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions(){
        app.goTo().homePage();
        if (app.contact().list().size()==0){
            app.goTo().contactPage();
            app.contact().create(new ContactData().withFirstName("Andrey").withLastName("Zakrenichnyy").withMiddleName("Nikolaevich").withEmail("zik2004@mail.ru").withGroup("test01").withPhone("1234567"), true);
        }
    }

    @Test
    public void testContactDeletion(){
        Set<ContactData> before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.goTo().homePage();
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size()-1);

        before.remove(deletedContact);
        Assert.assertEquals(before, after);
    }




}
