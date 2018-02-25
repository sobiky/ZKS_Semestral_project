package org.camunda.bpm.Elerning;

import org.camunda.bpm.Elerning.Model.Employee;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;
import java.util.logging.Logger;

import static org.camunda.bpm.engine.variable.Variables.objectValue;

public class CreateNewUserForEmployee implements JavaDelegate {
    private final static Logger LOGGER = Logger.getLogger("CreateNewUserForEmployee");
    private final static String NEW_PASSWORD = "testtest";

    public void execute(DelegateExecution delegateExecution) throws Exception {
        EntityManagerFactory ef = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = ef.createEntityManager();
        IdentityService identityService = delegateExecution.getProcessEngineServices().getIdentityService();

//User for database member group IP
        //        List<User> userList = delegateExecution.getProcessEngineServices()
//                .getIdentityService()
//                .createUserQuery()
//                .memberOfGroup("IT").list();
//        List<String> users = new ArrayList<String>();
//        for (User user : userList) {
//            users.add(user.getFirstName());
//        }

        //todo dodelat IT pracovnika
        LOGGER.info("////////////////////////");
        LOGGER.info("All variables");
        LOGGER.info(delegateExecution.getVariables().toString());
        LOGGER.info("////////////////////////");
        LOGGER.info("promenna uzivatelu : " + delegateExecution.getVariable("listUsers"));
        LOGGER.info("TEST :: ");

        Object test = delegateExecution.getVariable("listUsers");
        TypedValue customer = delegateExecution.getVariableTyped("listUsers");
        List<Map<String, String>> data = PavelMagicParser(delegateExecution,customer);

        //create new user to database
        List<User> userList = new ArrayList<>();
        for (Map<String, String> aData : data) {
            LOGGER.info("*********");
            LOGGER.info("firstName=" + aData.get("firstName"));
            LOGGER.info("lastName=" + aData.get("lastName"));
            LOGGER.info("email=" + aData.get("email"));
            LOGGER.info("department" + aData.get("department"));

            String userName = createNewUserName(aData.get("firstName"), aData.get("lastName"));
            User user = identityService.newUser(userName);
            user.setFirstName(aData.get("firstName"));
            user.setLastName(aData.get("lastName"));
            user.setEmail(aData.get("email"));
            // static password for all created users
            user.setPassword(NEW_PASSWORD);

            //First persist to database
            em.getTransaction().begin();

            Employee employee = new Employee();
            employee.setId(0);
            employee.setUserName(userName);
            employee.setDepartment("test");

            em.persist(employee);
            em.getTransaction().commit();

            em.close();
            ef.close();

            LOGGER.info(user.toString());

            identityService.saveUser(user);
            userList.add(user);
            identityService.createMembership(userName, "Employee");
        }
        List<String> users = new ArrayList<>();
        for (User user : userList) {
            users.add(user.getFirstName());
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
    //todo udelat lepe generovani  username pocitat s tim,
    //todo ze je mozne m9t vice lidi s stejnym username a treba pridat cislovani
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
    private List<Map<String, String>> PavelMagicParser(DelegateExecution delegateExecution,TypedValue customer){

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
        return data;
    }
}
