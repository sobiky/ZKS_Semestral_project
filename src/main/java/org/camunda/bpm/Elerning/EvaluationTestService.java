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
        boolean[] arraySuccessKeys = new boolean[]{true, false, false};
        LOGGER.info("KEYS"+ Arrays.toString(arraySuccessKeys) +"\n");
        evaulate(arraySuccessKeys,delegateExecution);
//        delegateExecution.setVariable("result", Boolean.TRUE);
        LOGGER.info("\n" + "test1 > " + delegateExecution.getVariable("otazka01") + "\n" +
                "test2 > " + delegateExecution.getVariable("otazka02") + "\n" +
                "test3 > " + delegateExecution.getVariable("otazka03") + "\n" +
                "result > " + delegateExecution.getVariable("result") + "\n");
//        LOGGER.info(delegateExecution.getProcessInstanceId());
//todo asi good cesta k moznosti vytvoreni tasku
        //        DelegateExecution delegate = delegateExecution.getProcessInstance();
//        delegate.setVariable("requestor","demo");
//        ProcessEngine processEngine = BpmPlatform.getProcessEngineService().getDefaultProcessEngine();
//        processEngine.getRuntimeService()
//  LOGGER.info();
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
