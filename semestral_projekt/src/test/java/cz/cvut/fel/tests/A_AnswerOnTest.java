package cz.cvut.fel.tests;


import cz.cvut.fel.model.User;
import cz.cvut.fel.pages.HomeWebPage;
import cz.cvut.fel.pages.LoginPage;
import cz.cvut.fel.task.FillTestWithCorrectAnswer;
import cz.cvut.fel.task.FillTestWithWrongAnswer;
import cz.cvut.fel.task.LoginTo;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class A_AnswerOnTest {

    private ChromeOptions options = new ChromeOptions();

    private User userEmployee;
    private User userITEmployee;
    private static final String ELEARNING = "Elearning";
    private static final String ELEARNING_FINISH = "Define all inf.system";
    private static final String ELEARNING_DONE = "No task matching filters found.";
    @Managed(driver = "chrome")
    public WebDriver browser;

    //    private Basket basket;
    @Before
    public void init() {
        userEmployee = readLogin("LoginEmployee");
        userITEmployee = readLogin("LoginITEmployee");
    }

    @Test
    public void A_signUp() {
        browser.get("http://localhost:8080/camunda/");
        LoginPage loginPage = new LoginPage(browser);
        assertTrue(loginPage.isInitialized());

        loginPage.enterUserName(userEmployee.getName());
        loginPage.enterPassword(userEmployee.getPassword());

        HomeWebPage homeWebPage = loginPage.submit();
        assertTrue(homeWebPage.isInitialized());
        assertEquals("Pavel Hrusdaf", homeWebPage.confirmationName());

    }

    @Test
    public void B_shouldBeAbleFillTestWithWrongAnswer() {
        options.addArguments("window-size=1400,800");
        options.addArguments("headless");

        Actor gorge = Actor.named("George");
        gorge.can(BrowseTheWeb.with(browser));

        when(gorge).attemptsTo(LoginTo.loginUser(userEmployee),
                FillTestWithWrongAnswer.fillTest());
        then(gorge).should(seeThat(Elearning.value(), equalTo(ELEARNING)));
    }

    @Test
    public void C_shouldBeAbleFillTestWithCorrectAnswer() {
        options.addArguments("window-size=1400,800");
        options.addArguments("headless");

        Actor employee = Actor.named(userEmployee.getName());
        employee.can(BrowseTheWeb.with(browser));

        when(employee).attemptsTo(LoginTo.loginUser(userEmployee),
                FillTestWithCorrectAnswer.fillTest());
        then(employee).should(seeThat(ElearningDONE.value(), equalTo(ELEARNING_DONE)));

    }

    @Test
    public void D_shouldBeAbleFillTestWithCorrectAnswerFromITEmployee() {
        options.addArguments("window-size=1400,800");
        options.addArguments("headless");

        Actor ITEmployee = Actor.named(userITEmployee.getName());
        ITEmployee.can(BrowseTheWeb.with(browser));

        when(ITEmployee).attemptsTo(LoginTo.loginUser(userITEmployee),
                FillTestWithCorrectAnswer.fillTest());
        then(ITEmployee).should(seeThat(Elearning.value(), equalTo(ELEARNING_FINISH)));
    }


    private static class Elearning implements Question<String> {
        public Elearning() {
        }

        static Question<String> value() {
            return new Elearning();
        }

        @Override
        public String answeredBy(Actor actor) {
            return BrowseTheWeb.as(actor).findBy("/html/body/div[2]/div/div/section[2]/div/div/div[3]/div/ol/li/div/div[1]/h4/a").getText();
        }
    }

    private static class ElearningDONE implements Question<String> {
        public ElearningDONE() {
        }

        static Question<String> value() {
            return new ElearningDONE();
        }

        @Override
        public String answeredBy(Actor actor) {
            return BrowseTheWeb.as(actor).findBy("/html/body/div[2]/div/div/section[2]/div/div/div[3]/div").getText();
        }
    }

    public User readLogin(String fileName){
        BufferedReader reader;
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
           User user = new User();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            user.setName(line);
            line = reader.readLine();
            user.setPassword(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
}