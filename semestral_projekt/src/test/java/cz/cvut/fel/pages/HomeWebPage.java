package cz.cvut.fel.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomeWebPage extends PageObject {

    @FindBy(xpath ="/html/body/div[1]/nav/ul/li[2]/a")
    private WebElement name;

    @FindBy(xpath = "/html/body/div[2]/div/div/section[2]/div/div/div[3]/div/ol/li/div")
    private WebElement submenu;

    @FindBy(xpath = "/html/body/div[2]/div/div/section[3]/div/div/div[2]/section/div/div/div/view/div/div/div[2]/div[2]/div/div/div/div/form/div/h2")
    private WebElement header;

    public HomeWebPage(WebDriver driver){
        super(driver);
    }

    public void clickSubmenu(){
        submenu.click();
    }

    public String getHeader(){
       return header.getText();
    }
    public String confirmationName(){
        return name.getText();
    }

    public boolean isInitialized() {
        return name.isDisplayed();
    }
}
