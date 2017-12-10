package org.camunda.bpm.Elerning;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.variable.Variables;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.camunda.bpm.engine.variable.Variables.objectValue;

public class SelectedUserFromDatabaseService implements JavaDelegate {
    private final static Logger LOGGER = Logger.getLogger("SelectedUserFromDatabaseService");
    private final static String NEW_PASSWORD = "testtest";

    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<User> userList = delegateExecution.getProcessEngineServices()
                .getIdentityService()
                .createUserQuery()
                .memberOfGroup("IT").list();
        List<String> users = new ArrayList<String>();
        for (User user : userList) {
            users.add(user.getFirstName());
        }

        LOGGER.info("promenna uzivatelu : "+ delegateExecution.getVariables());
        LOGGER.info("test pole");






        delegateExecution.setVariable("listUsersRaw", userList);
        delegateExecution.setVariable("sizeListUserRaw", userList.size());
//     serialization variables
        delegateExecution.setVariable("names", Variables.objectValue(users.toArray())
                .serializationDataFormat(Variables.SerializationDataFormats.JSON).create());
        delegateExecution.setVariable("listUserJson", objectValue(userList)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON).create());
    }

    private void createName(String fName, String lName) {
        StringBuilder stringBuilder = new StringBuilder();
        if (fName.length() <= 3 || lName.length() <= 3) {
            if (fName.length() <= 3) {
                stringBuilder.append(fName);
            } else {
                stringBuilder.append(lName);
            }
        }
        stringBuilder.append(fName.substring(0, 3));
        stringBuilder.append(lName.substring(0, 3));
    }
}
