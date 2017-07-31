package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import java.io.File;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        app.goTo().contactPage();
        File photo = new File("src/test/resources/smile.png");
        ContactData contact = new ContactData().withFirstName("Andrey").withLastName("Zakrenichnyy").withMiddleName("Nikolaevich")
                .withGroup("test01").withHomePhone("1234567").withMobilePhone("89261234567").withWorkPhone("1234")
                .withEmail("qwerty1@mail.ru").withEmail2("qwerty1@mail.ru").withEmail3("qwerty3@mail.ru").withPhoto(photo);

        app.contact().create(contact, true);
        assertThat(app.contact().count(), equalTo(before.size()+1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt()))));
    }

}
