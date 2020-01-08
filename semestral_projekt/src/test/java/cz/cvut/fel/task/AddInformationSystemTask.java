package cz.cvut.fel.task;

import cz.cvut.fel.model.InformationSystem;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;

public class AddInformationSystemTask implements Task {
    private InformationSystem informationSystem;

    public AddInformationSystemTask(InformationSystem informationSystem) {
        this.informationSystem = informationSystem;
    }

    @Override
    @Step("AddInfSystem")
    public <T extends Actor> void performAs(T t) {
            t.attemptsTo(
                    Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-center.tasks-list.ng-scope > div > div > div.ng-scope > div > ol > li > div")),
                    Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-right.task-details.ng-scope > div > div > div:nth-child(2) > section > div > div > div > view > div > div > div.ng-scope > div:nth-child(2) > div > div > div > div > form > div > div > button")),
                    Enter.theValue(informationSystem.getName()).into("/html/body/div[2]/div/div/section[3]/div/div/div[2]/section/div/div/div/view/div/div/div[2]/div[2]/div/div/div/div/form/div/div/div/table/tbody/tr[2]/td[2]/input"),
                    Enter.theValue(informationSystem.getUrl()).into("/html/body/div[2]/div/div/section[3]/div/div/div[2]/section/div/div/div/view/div/div/div[2]/div[2]/div/div/div/div/form/div/div/div/table/tbody/tr[3]/td[2]/input"),
                    Enter.theValue(informationSystem.getDescription()).into("/html/body/div[2]/div/div/section[3]/div/div/div[2]/section/div/div/div/view/div/div/div[2]/div[2]/div/div/div/div/form/div/div/div/table/tbody/tr[4]/td[2]/input"),
                    Enter.theValue(informationSystem.getAdmName()).into("/html/body/div[2]/div/div/section[3]/div/div/div[2]/section/div/div/div/view/div/div/div[2]/div[2]/div/div/div/div/form/div/div/div/table/tbody/tr[6]/td[2]/input"),
                    Enter.theValue(informationSystem.getAdmEmail()).into("/html/body/div[2]/div/div/section[3]/div/div/div[2]/section/div/div/div/view/div/div/div[2]/div[2]/div/div/div/div/form/div/div/div/table/tbody/tr[7]/td[2]/input"),
                    Enter.theValue(informationSystem.getAdmPhone()).into("/html/body/div[2]/div/div/section[3]/div/div/div[2]/section/div/div/div/view/div/div/div[2]/div[2]/div/div/div/div/form/div/div/div/table/tbody/tr[8]/td[2]/input"),
                    Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-right.task-details.ng-scope > div > div > div:nth-child(2) > section > div > div > div > view > div > div > div.ng-scope > div.form-actions > button:nth-child(2)")),
                    Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-center.tasks-list.ng-scope > div > div > div.ng-scope > div > ol > li > div")),
                    Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-right.task-details.ng-scope > div > div > div:nth-child(2) > section > div > div > div > view > div > div > div.ng-scope > div.form-actions > button:nth-child(2)"))
            );
    }
    public static Performable addINFSystem(InformationSystem informationSystem){
        return Instrumented.instanceOf(AddInformationSystemTask.class).withProperties(informationSystem);
    }
}
