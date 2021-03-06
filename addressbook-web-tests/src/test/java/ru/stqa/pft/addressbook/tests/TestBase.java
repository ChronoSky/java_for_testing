package ru.stqa.pft.addressbook.tests;


import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);
    // IE
    // CHROME
    // FIREFOX
     protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));


    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p){
        logger.info("Начало теста <"+ m.getName() +"> с параметрами "+ Arrays.asList(p));

    }

    @BeforeMethod(alwaysRun = true)
    public void logTestStop(Method m){
        logger.info("Окончание теста <"+ m.getName() +">");
    }

    public void verifyGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")){
            Groups dbGroups = app.db().groups();
            Groups uiGRoups = app.group().all();
            assertThat(uiGRoups, equalTo(dbGroups.stream().map((g)->new GroupData().withId(g.getId()).withName(g.getName())).collect(Collectors.toSet())));
        }

    }
}
