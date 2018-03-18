package org.camunda.bpm.Elerning.Services;

import org.camunda.bpm.Elerning.Model.ITDepartment;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.variable.Variables;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.*;
import java.util.logging.Logger;

import static org.camunda.bpm.engine.variable.Variables.objectValue;

public class ITSystemService implements JavaDelegate {
    private final static Logger LOGGER = Logger.getLogger("ITSystemService");

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        EntityManagerFactory ef = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = ef.createEntityManager();
        Query query = em.createQuery("select e from ITDepartment e where e.tenant = :id");
        query.setParameter("id", "tututu");
        List<ITDepartment> result = query.getResultList();
        List<String> department = new ArrayList<String>();
        for (ITDepartment item : result) {
            department.add(item.getName());
            LOGGER.info("----------------------------------");
            LOGGER.info(item.getName());
            LOGGER.info("----------------------------------");
        }

        delegateExecution.setVariable("departmentsList", Variables.objectValue(department.toArray())
                .serializationDataFormat(Variables.SerializationDataFormats.JSON).create());
    }
}
