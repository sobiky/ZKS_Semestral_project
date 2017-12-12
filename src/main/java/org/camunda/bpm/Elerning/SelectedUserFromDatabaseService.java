package org.camunda.bpm.Elerning;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.*;
import java.util.logging.Logger;

import static org.camunda.bpm.engine.variable.Variables.objectValue;

public class SelectedUserFromDatabaseService implements JavaDelegate {
    private final static Logger LOGGER = Logger.getLogger("SelectedUserFromDatabaseService");
    private final static String NEW_PASSWORD = "testtest";

    public void execute(DelegateExecution delegateExecution) throws Exception {
        IdentityService userService = delegateExecution.getProcessEngineServices().getIdentityService();
        List<User> userList = delegateExecution.getProcessEngineServices()
                .getIdentityService()
                .createUserQuery()
                .memberOfGroup("IT").list();
        List<String> users = new ArrayList<String>();
        for (User user : userList) {
            users.add(user.getFirstName());
        }
        Object test = delegateExecution.getVariable("listUsers");
        LOGGER.info("promenna uzivatelu : " + delegateExecution.getVariable("listUsers"));

        LOGGER.info("TEST :: ");
        TypedValue customer = delegateExecution.getVariableTyped("listUsers");
        String[] userStrings = customer.getValue().toString()
                .substring(1, customer.getValue().toString().length() - 1).split("},\\{");
        List<Map<String, String>> data = new ArrayList<>(userStrings.length);

        for (String userString : userStrings) {
            //[{"firstName":"dsad","lastName":"awf","email":"","group":"aefea","$$hashKey":"07S"
            String[] pairs = userString.substring(1).split("\",\"");
            HashMap<String, String> map = new HashMap<>();
            for (String pair : pairs) {
                String[] keyValue = pair.split("\":\"");
                if (keyValue[0].startsWith("\"")) {
                    keyValue[0] = keyValue[0].substring(1);
                }
                if (keyValue.length == 2 && keyValue[1].endsWith("\"")) {
                    keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - 1);
                }
                map.put(keyValue[0], keyValue.length == 2 ? keyValue[1] : "");
            }
            data.add(map);
        }
        //create new user to database
        for (int i = 0; i < data.size(); i++) {
            LOGGER.info("*********");
            LOGGER.info("firstName=" + data.get(i).get("firstName"));
            LOGGER.info("lastName=" + data.get(i).get("lastName"));
            LOGGER.info("email=" + data.get(i).get("email"));
            User user = userService.newUser(createNewUserName(data.get(i).get("firstName"), data.get(i).get("lastName")));
            user.setFirstName(data.get(i).get("firstName"));
            user.setLastName(data.get(i).get("lastName"));
            user.setEmail(data.get(i).get("email"));
            // static password for all created users
            user.setPassword("testtest");
            LOGGER.info(user.toString());
            userService.saveUser(user);
        }


        LOGGER.info(customer.getValue().toString());
        LOGGER.info("Type is: " + customer.getType().getName());


        delegateExecution.setVariable("listUsersRaw", userList);
        delegateExecution.setVariable("sizeListUserRaw", userList.size());
//     serialization variables
        delegateExecution.setVariable("names", Variables.objectValue(users.toArray())
                .serializationDataFormat(Variables.SerializationDataFormats.JSON).create());
        delegateExecution.setVariable("listUserJson", objectValue(userList)
                .serializationDataFormat(Variables.SerializationDataFormats.JSON).create());
    }

    // funcion for generated user name
    private String createNewUserName(String fName, String lName) {
        StringBuilder stringBuilder = new StringBuilder();
        if (fName.length() <= 3 || lName.length() <= 3) {
            if (lName.length() <= 3) {
                stringBuilder.append(lName);
            } else {
                stringBuilder.append(fName);
            }
        }
        stringBuilder.append(lName.substring(0, 3));
        stringBuilder.append(fName.substring(0, 3));
        return stringBuilder.toString();
    }
}
