package ru.stqa.pft.mantis.tests;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class RegistrationTests extends TestBase{

   @BeforeMethod
    public void startMailServer(){
        app.mail().start();
    }

    @Test
    public void testRegistretion() throws IOException, MessagingException {
        long now = System.currentTimeMillis();
        String user = String.format("user_%s" , now);
        String password = "password";
        String email = String.format("user_%s@localhost.localdomain",now);
        app.registation().start(user, email);
        List<MailMessage> mailMassages = app.mail().waitForMail(2, 10000);
        String confirmationLink = findConfirmationLink(mailMassages, email);
        app.registation().finish(confirmationLink, password);
        app.newSession().login(user, password);
    }

    private String  findConfirmationLink(List<MailMessage> mailMassages, String email) {
        MailMessage mailMessage = mailMassages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.mail().stop();
    }

}
