package cz.cvut.fel.tests;

import cz.cvut.fel.model.ElectronicForm;
import cz.cvut.fel.model.PaperForm;
import cz.cvut.fel.model.User;
import cz.cvut.fel.task.AddElectronicForm;
import cz.cvut.fel.task.AddPaperForm;
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

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class C_AddElectronicAndPaperFrom {
    private User userITEmployee;
    private User userEmployee;
    private ChromeOptions options = new ChromeOptions();
    private static final String END = "Supplier";

    @Managed(driver = "chrome")
    private WebDriver browser;

    private ElectronicForm electronicForm;
    private PaperForm paperForm;
    @Before
    public void init(){
        userEmployee = readLogin("LoginEmployee");
        userITEmployee = readLogin("LoginITEmployee");
        readElectroAndParerFromData();
    }

    @Test
    public void A_shouldBeAbleToAddElectronicForm() {
        options.addArguments("window-size=1400,800");
        options.addArguments("headless");

        Actor gorge = Actor.named("George");
        gorge.can(BrowseTheWeb.with(browser));

        when(gorge).attemptsTo(LoginTo.loginUser(userITEmployee),
                AddElectronicForm.addElectronicForm(electronicForm));
        then(gorge).should(seeThat(C_AddElectronicAndPaperFrom.AddElectonicAndPaperForm.value(),equalTo(END)));
    }
    @Test
    public void B_shouldBeAbleToAddPaperForm() {
        options.addArguments("window-size=1400,800");
        options.addArguments("headless");

        Actor gorge = Actor.named("George");
        gorge.can(BrowseTheWeb.with(browser));

        when(gorge).attemptsTo(LoginTo.loginUser(userEmployee),
                AddPaperForm.addPaperForm(paperForm));
        then(gorge).should(seeThat(C_AddElectronicAndPaperFrom.AddElectonicAndPaperForm.value(),equalTo(END)));
    }

    private static class AddElectonicAndPaperForm implements Question<String> {
        public AddElectonicAndPaperForm(){}

        static Question<String> value() {
            return new AddElectonicAndPaperForm();
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

    public void readElectroAndParerFromData(){
        BufferedReader reader;
        File file = new File(getClass().getClassLoader().getResource("ElektroAndPaperForm").getFile());
        electronicForm =new ElectronicForm();
        paperForm = new PaperForm();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            electronicForm.setNetworkDisk(line);
            line = reader.readLine();
            paperForm.setStorage(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
