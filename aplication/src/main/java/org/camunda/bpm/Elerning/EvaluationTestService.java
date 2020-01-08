package org.camunda.bpm.Elerning;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Arrays;
import java.util.logging.Logger;

public class EvaluationTestService implements JavaDelegate {
    private final static Logger LOGGER = Logger.getLogger("EvaluationTest");

    public void execute(DelegateExecution delegateExecution) throws Exception {
        //tohle je klic pro uspeny pruchod
        boolean[] arraySuccessKeys = new boolean[]{
                false, false, false, true,
                false, false, false, true,
                false, true, false, false,
                false, false, true, false,
                true, false, false, false,
        };
        LOGGER.info("KEYS" + Arrays.toString(arraySuccessKeys) + "\n");
        evaulate(arraySuccessKeys, delegateExecution);

        LOGGER.info("\n" + "test1 > " + delegateExecution.getVariable("otazka1") + "\n" +
                "test2 > " + delegateExecution.getVariable("otazka2") + "\n" +
                "test3 > " + delegateExecution.getVariable("otazka3") + "\n" +
                "result > " + delegateExecution.getVariable("result") + "\n");
            removeAllCheckBoxs(arraySuccessKeys,delegateExecution);
    }

    private void evaulate(boolean[] booleans, DelegateExecution delegate) {
        boolean temp = true;
        int i = 1;
        for (boolean value : booleans) {
            if (value != (Boolean) delegate.getVariable("otazka" + i)) {
                temp = false;
            }
            i++;
        }
        delegate.setVariable("result", temp);
    }
    private void removeAllCheckBoxs(boolean[] booleans,DelegateExecution delegate){
        int i = 1;
        for (boolean value : booleans) {
            delegate.removeVariable("otazka" + i);
            i++;
        }
    }

}
