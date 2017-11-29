package org.camunda.bpm.Elerning;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

public class SelectedUserService implements JavaDelegate {
        private final static Logger LOGGER = Logger.getLogger("SelectedUserService");
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("requestor",delegateExecution.getVariable("selectBoxUsers"));
    }
}
