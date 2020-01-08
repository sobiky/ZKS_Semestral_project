package cz.cvut.fel.task;

import cz.cvut.fel.model.User;
import cz.cvut.fel.pages.CamundaWebPage;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class LoginTo implements Task {
    private CamundaWebPage camundaWebPage;
    private User user;

    LoginTo(User user){this.user = user;}

    @Override
    @Step("login")
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(Open.browserOn(camundaWebPage),
                Click.on(By.cssSelector("#signinFormInputUsername")),
                Enter.theValue(user.getName()).into("#signinFormInputUsername"),
                Enter.theValue(user.getPassword()).into("#signinFormInputPassword").thenHit(Keys.ENTER));
    }

    public static Performable loginUser(User user){
        return Instrumented.instanceOf(LoginTo.class).withProperties(user);
    }
}
