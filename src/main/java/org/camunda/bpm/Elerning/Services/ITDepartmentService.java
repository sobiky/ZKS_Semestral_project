package org.camunda.bpm.Elerning.Services;

import org.camunda.bpm.Elerning.CreateNewUserForEmployee;
import org.camunda.bpm.Elerning.Model.AdministratorITSystem;
import org.camunda.bpm.Elerning.Model.ITDepartment;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.TypedValue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;
import java.util.logging.Logger;

public class ITDepartmentService implements JavaDelegate {
    private ITDepartment itDepartment;
    private final static Logger LOGGER = Logger.getLogger("ITDepartment");
    EntityManagerFactory ef = Persistence.createEntityManagerFactory("Eclipselink_JPA");
    EntityManager em = ef.createEntityManager();

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("---------VARIABLES---------");
        LOGGER.info(delegateExecution.getVariables().toString());
        LOGGER.info("---------VARIABLES---------");
        LOGGER.info(delegateExecution.getVariable("listInformationSystem").toString());
        TypedValue itDepartments = delegateExecution.getVariableTyped("listInformationSystem");
        List<Map<String, String>> data = CreateNewUserForEmployee.PavelMagicParser(itDepartments);
        for (Map<String, String> item : data) {

            em.getTransaction().begin();

            //todo podivat se na to co mam poslat drive a popripade poprosit pavla
            ITDepartment department = new ITDepartment();
            department.setITDepartmentId(0);
            department.setDescription(item.get("description"));
            department.setName(item.get("name"));
            department.setUrl(item.get("url"));
            department.setTenant(delegateExecution.getVariable("tenant").toString());

            AdministratorITSystem admin = new AdministratorITSystem();
            admin.setAdminId(0);
            admin.setAdminName(item.get("adminName"));
            admin.setEmail(item.get("adminEmail"));
            admin.setPhone(item.get("adminTel"));
            admin.setSystem(department);


            department.addAdmin(admin);


            LOGGER.info("---------BEGIN---------");
            LOGGER.info(department.toString());


            em.persist(admin);
//            em.getTransaction().commit();

//            em.getTransaction().begin();
            em.persist(department);
            em.getTransaction().commit();
            LOGGER.info("---------COMMIT---------");


            for (Map.Entry<String, String> pair : item.entrySet()) {
                LOGGER.info("/" + pair.getKey() + " >>> " + pair.getValue());
            }
        }
        em.close();
        ef.close();
    }



}
