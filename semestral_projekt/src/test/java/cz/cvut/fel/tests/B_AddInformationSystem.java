package cz.cvut.fel.tests;

import cz.cvut.fel.model.InformationSystem;
import cz.cvut.fel.model.User;
import cz.cvut.fel.task.AddInformationSystemTask;
import cz.cvut.fel.task.LoginTo;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static net.serenitybdd.screenplay.GivenWhenThen.*;


@RunWith(SerenityRunner.class)
public class B_AddInformationSystem {
    private static final String FINISH = "Processing of personal data";
    private User userITEmployee;
    private InformationSystem informationSystem;
    private ChromeOptions options = new ChromeOptions();
    @Managed(driver = "chrome")
    private WebDriver browser;

    @Before
    public void init(){
        userITEmployee = readLogin("LoginITEmployee");
        informationSystem = readDataForInfSystem("InformationSystem");
    }

    @Test
    public void A_shouldBeAbleToAddInformationSystem() {
        options.addArguments("window-size=1400,800");
        options.addArguments("headless");

        Actor gorge = Actor.named("George");
        gorge.can(BrowseTheWeb.with(browser));

        when(gorge).attemptsTo(LoginTo.loginUser(userITEmployee),
                AddInformationSystemTask.addINFSystem(informationSystem));
        then(gorge).should(seeThat(AddInformationSystemClass.value(),equalTo(FINISH)));
    }
    private static class AddInformationSystemClass implements Question<String> {
        public AddInformationSystemClass(){}

        static Question<String> value() {
            return new AddInformationSystemClass();
        }

        @Override
        public String answeredBy(Actor actor) {
            return BrowseTheWeb.as(actor).findBy("/html/body/div[2]/div/div/section[2]/div/div/div[3]/div/ol/li/div/div[1]/h4/a").getText();
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

    public InformationSystem readDataForInfSystem(String fileName){
        BufferedReader reader;
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        InformationSystem inf = new InformationSystem();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            inf.setName(line);
            line = reader.readLine();
            inf.setUrl(line);
            line = reader.readLine();
            inf.setDescription(line);
            line = reader.readLine();
            inf.setAdmName(line);
            line = reader.readLine();
            inf.setAdmEmail(line);
            line = reader.readLine();
            inf.setAdmPhone(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inf;
    }
}
