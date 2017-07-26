package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactInformationTest extends TestBase{
    FirefoxDriver wd;

    @Test
    public void testContactCreation() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getEmail(), equalTo(contactInfoFromEditForm.getEmail()));
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
    }

    private String mergePhones(ContactData contactInfoFromEditForm) {
        String result = "";
        return Arrays.asList(contactInfoFromEditForm.getHomePhone(), contactInfoFromEditForm.getMobilePhone(), contactInfoFromEditForm.getWorkPhone())
                .stream().filter((s)-> !s.equals("")).collect(Collectors.joining("\n"));
    }
}
