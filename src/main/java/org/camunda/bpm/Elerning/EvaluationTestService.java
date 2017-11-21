package org.camunda.bpm.Elerning;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class EvaluationTestService implements JavaDelegate {
    private boolean[] arraySuccessKeys;
    private final static Logger LOGGER = Logger.getLogger("EvaluationTest");

    public void execute(DelegateExecution delegateExecution) throws Exception {

        //tohle je klic pro uspeny pruchod
        arraySuccessKeys = new boolean[]{true, false, false};
        LOGGER.info("KEYS"+arraySuccessKeys+"\n");
        evaulate(arraySuccessKeys,delegateExecution);
//        delegateExecution.setVariable("result", Boolean.TRUE);
        LOGGER.info("\n" + "test1 > " + delegateExecution.getVariable("otazka01") + "\n" +
                "test2 > " + delegateExecution.getVariable("otazka02") + "\n" +
                "test3 > " + delegateExecution.getVariable("otazka03") + "\n" +
                "result > " + delegateExecution.getVariable("result") + "\n");
    }

    private void evaulate(boolean[] booleans, DelegateExecution delegate) {
        boolean temp = true;
        int i = 1;
        for (boolean value : booleans) {
            if(value != (Boolean) delegate.getVariable("otazka0"+i)){
                temp=false;
            }
            i++;
        }
        delegate.setVariable("result",temp);
    }

}
