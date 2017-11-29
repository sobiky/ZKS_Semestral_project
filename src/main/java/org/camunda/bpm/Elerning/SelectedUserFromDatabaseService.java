package org.camunda.bpm.Elerning;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.variable.Variables;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.camunda.bpm.engine.variable.Variables.objectValue;

public class SelectedUserFromDatabaseService implements JavaDelegate {
    private final static Logger LOGGER = Logger.getLogger("SelectedUserFromDatabaseService");
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<User> userList = delegateExecution.getProcessEngineServices()
                .getIdentityService()
                .createUserQuery()
                .memberOfGroup("IT").list();
        List<String> users = new ArrayList<String>();
        for (User user:userList) {
            users.add(user.getFirstName());
        }
        LOGGER.info("test funkcnosti : "+ Arrays.toString(userList.toArray()));
        LOGGER.info("test funkcnosti : "+ Arrays.toString(users.toArray()));
        delegateExecution.setVariable("listNames",userList);
//     serialization variables
        delegateExecution.setVariable("names", Variables.objectValue(users.toArray())
                .serializationDataFormat(Variables.SerializationDataFormats.JSON).create());
        delegateExecution.setVariable("listUser",objectValue(userList)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON).create());
    }
}
