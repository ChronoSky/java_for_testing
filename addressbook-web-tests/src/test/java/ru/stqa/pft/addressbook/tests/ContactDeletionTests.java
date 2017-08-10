package ru.stqa.pft.addressbook.tests;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase{



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
    public void testContactDeletion(){
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size()-1));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.withoutAdded(deletedContact)));
    }
}
