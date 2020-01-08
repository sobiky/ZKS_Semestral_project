package cz.cvut.fel;


import cz.cvut.fel.model.User;
import cz.cvut.fel.task.LoginTo;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.questions.page.TheWebPage;
import net.thucydides.core.annotations.Managed;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.openqa.selenium.chrome.ChromeOptions;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.Matchers.containsString;


@RunWith(SerenityRunner.class)
public class CamundaLoginTest {

    private ChromeOptions options = new ChromeOptions();


    @Managed(driver = "chrome")
    private WebDriver browser;



    @Test
    public void shouldBeAbleToNavigateToBasket() {
        options.addArguments("window-size=1400,800");
        options.addArguments("headless");

        Actor gorge = Actor.named("George");
        gorge.can(BrowseTheWeb.with(browser));

        when(gorge).attemptsTo(LoginTo.loginUser(new User()));
        then(gorge).should(seeThat(TheWebPage.title(), containsString("Camunda Tasklist")));
    }
}