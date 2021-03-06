package cz.cvut.fel.task;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;

public class FillTestWithEmptyAnswer implements Task {
    @Override
    @Step("FillTestWithEmptyAnswer")
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(
                Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-center.tasks-list.ng-scope > div > div > div.ng-scope > div > ol > li > div")),
                Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-right.task-details.ng-scope > div > div > div:nth-child(2) > section > div > div > div > view > div > div > div.ng-scope > div.form-actions > button:nth-child(2)")),
                Click.on(By.cssSelector("body > div.page-notifications.ng-isolate-scope > div > div > button")),
                Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-center.tasks-list.ng-scope > div > div > div.ng-scope > div > ol > li > div")),
                Click.on(By.xpath("/html/body/div[2]/div/div/section[3]/div/div/div[2]/section/div/div/div/view/div/div/div[2]/div[3]/button[2]")));
    }
    public static Performable fillTest(){
        return Instrumented.instanceOf(FillTestWithEmptyAnswer.class).withProperties();
    }
}
