package cz.cvut.fel.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {

    @FindBy(id = "signinFormInputUsername")
    private WebElement loginName;

    @FindBy(id = "signinFormInputPassword")
    private WebElement password;

    @FindBy(xpath = "/html/body/div[2]/div/div/form/div[2]/button")
    private WebElement submitButton;

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public void enterUserName(String userName){
        this.loginName.clear();
        this.loginName.sendKeys(userName);
    }

    public void enterPassword(String password){
        this.password.clear();
        this.password.sendKeys(password);
    }
    public HomeWebPage submit(){
        submitButton.click();
        return new HomeWebPage(this.getDriver());
    }

    public boolean isInitialized() {
        return loginName.isDisplayed();
    }
}
