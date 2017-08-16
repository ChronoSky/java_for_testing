package ru.stqa.pft.mantis.tests;


import org.testng.annotations.Test;

public class RegistrationTests extends TestBase{

    @Test
    public void testRegistretion(){
        app.registation().start("user1","user1@localhost.localdomain");
    }
}
