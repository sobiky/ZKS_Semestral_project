package cz.cvut.fel.tests;



import cz.cvut.fel.model.User;
import cz.cvut.fel.pages.HomeWebPage;
import cz.cvut.fel.pages.LoginPage;
import net.serenitybdd.junit.runners.SerenityRunner;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class E_ProcesResultTest {

    private ChromeOptions options = new ChromeOptions();

    private User userAdmin;
    @Managed(driver = "chrome")
    public WebDriver browser;

    @Before
    public void init(){
        userAdmin = readLogin("LoginAdmin");
    }

    @Test
    public void ShouldByAbleToShowResult(){
        browser.get("http://localhost:8080/camunda/");
        LoginPage loginPage = new LoginPage(browser);
        assertTrue(loginPage.isInitialized());

        loginPage.enterUserName(userAdmin.getName());
        loginPage.enterPassword(userAdmin.getPassword());

        HomeWebPage homeWebPage = loginPage.submit();
        assertTrue(homeWebPage.isInitialized());
        homeWebPage.clickSubmenu();
        assertEquals("Organizace : ZKST1",homeWebPage.getHeader());

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