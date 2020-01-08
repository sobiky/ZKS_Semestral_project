package cz.cvut.fel.tests;

import cz.cvut.fel.model.Supplier;
import cz.cvut.fel.model.User;
import cz.cvut.fel.task.AddSupplierTask;
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
public class D_AddSupplierTest {
    private User userITEmployee;
    private User userEmployee;
    private Supplier supplier;
    private Supplier supplier1;
    private ChromeOptions options = new ChromeOptions();
    private static final String END = "No task matching filters found.";

    @Managed(driver = "chrome")
    private WebDriver browser;


    @Before
    public void init(){
        userEmployee = readLogin("LoginEmployee");
        userITEmployee = readLogin("LoginITEmployee");

        supplier = readSupplier("Supplier");

        supplier1 = new Supplier();
        supplier1.setName("Dovatel Plynu test");
        supplier1.setDescription("tohle je popis dodavatele plynu");


    }

    @Test
    public void A_shouldBeAbleToAddSupplier() {
        options.addArguments("window-size=1400,800");
        options.addArguments("headless");

        Actor gorge = Actor.named("George");
        gorge.can(BrowseTheWeb.with(browser));

        when(gorge).attemptsTo(LoginTo.loginUser(userITEmployee),
                AddSupplierTask.addSupplier(supplier));
        then(gorge).should(seeThat(D_AddSupplierTest.AddSupplier.value(),equalTo(END)));
    }

    @Test
    public void B_shouldBeAbleToAddSupplier() {
        options.addArguments("window-size=1400,800");
        options.addArguments("headless");

        Actor gorge = Actor.named("George");
        gorge.can(BrowseTheWeb.with(browser));

        when(gorge).attemptsTo(LoginTo.loginUser(userEmployee),
                AddSupplierTask.addSupplier(supplier1));
        then(gorge).should(seeThat(D_AddSupplierTest.AddSupplier.value(),equalTo(END)));
    }

    private static class AddSupplier implements Question<String> {
        public AddSupplier(){}

        static Question<String> value() {
            return new AddSupplier();
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
    public Supplier readSupplier(String fileName){
        BufferedReader reader;
        File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
        Supplier supplier = new Supplier();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            supplier.setName(line);
            line = reader.readLine();
            supplier.setDescription(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return supplier;
    }
}
