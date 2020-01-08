package cz.cvut.fel.task;

import cz.cvut.fel.model.ElectronicForm;
import cz.cvut.fel.model.PaperForm;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;

public class AddPaperForm implements Task {
    private PaperForm paperForm;

    public AddPaperForm(PaperForm paperForm) {
        this.paperForm = paperForm;
    }

    @Override
    @Step("AddPaperForm")
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(
                Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-center.tasks-list.ng-scope > div > div > div.ng-scope > div > ol > li > div")),
                Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-right.task-details.ng-scope > div > div > div:nth-child(2) > section > div > div > div > view > div > div > div.ng-scope > div:nth-child(2) > div > div > div > div > form > div > input:nth-child(7)")),
                Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-right.task-details.ng-scope > div > div > div:nth-child(2) > section > div > div > div > view > div > div > div.ng-scope > div:nth-child(2) > div > div > div > div > form > div > div:nth-child(11) > button")),
                Enter.theValue(paperForm.getStorage()).into("/html/body/div[2]/div/div/section[3]/div/div/div[2]/section/div/div/div/view/div/div/div[2]/div[2]/div/div/div/div/form/div/div[2]/div/input"),
                Click.on(By.cssSelector("body > div.site-wrap > div > div > section.column.column-right.task-details.ng-scope > div > div > div:nth-child(2) > section > div > div > div > view > div > div > div.ng-scope > div.form-actions > button:nth-child(2)"))
        );
    }

    public static Performable addPaperForm(PaperForm paperForm){
        return Instrumented.instanceOf(AddPaperForm.class).withProperties(paperForm);
    }
}
