package org.camunda.bpm.Elerning.Services;

import org.camunda.bpm.Elerning.CreateNewUserForEmployee;
import org.camunda.bpm.Elerning.Model.ElectronicData;
import org.camunda.bpm.Elerning.Model.PapperData;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.variable.value.TypedValue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class DocumentsService implements JavaDelegate {
    private final static Logger LOGGER = Logger.getLogger("DocumentsService");

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        EntityManagerFactory ef = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = ef.createEntityManager();
        em.getTransaction().begin();
        TypedValue itDepartments = delegateExecution.getVariableTyped("camundaDataUrl");
        TypedValue pappersData = delegateExecution.getVariableTyped("listPappers");
        TypedValue electronicsData = delegateExecution.getVariableTyped("listElectronic");

        if (!Objects.equals(pappersData.getValue().toString(), "[]")) {
            List<Map<String, String>> pappersList = CreateNewUserForEmployee.PavelMagicParser(pappersData);
            for (Map<String, String> item : pappersList) {
                PapperData papperData = new PapperData();
                papperData.setTenant(delegateExecution.getVariable("tenant").toString());
                papperData.setType("Papper");
                papperData.setPlace(item.get("place"));
                em.persist(papperData);

            }

        }
        if (!Objects.equals(electronicsData.getValue().toString(), "[]")) {
            List<Map<String, String>> electroDataList = CreateNewUserForEmployee.PavelMagicParser(electronicsData);
            LOGGER.info("//////////////////////LIST/////////////////");
            for (Map<String, String> item : electroDataList) {
                for (Map.Entry<String, String> pair : item.entrySet()) {
                    LOGGER.info("/" + pair.getKey() + " >>> " + pair.getValue());
                }

            }
            LOGGER.info("//////////////////////LIST/////////////////");

            for (Map<String, String> item : electroDataList) {
                ElectronicData electronicData = new ElectronicData();
                electronicData.setTenant(delegateExecution.getVariable("tenant").toString());
                if (!item.get("networkDisk").equals("")) {
                    electronicData.setNetworkDisk(item.get("networkDisk"));
                }
                electronicData.setType("Elektronic");
                electronicData.setITSystem(item.get("systemItem"));
                em.persist(electronicData);
            }


        }

        if (!Objects.equals(itDepartments.getValue().toString(), "[]")) {
            List<Map<String, String>> data = CreateNewUserForEmployee.PavelMagicParser(itDepartments);

            for (Map<String, String> item : data) {
                for (Map.Entry<String, String> pair : item.entrySet()) {
                    LOGGER.info("/" + pair.getKey() + " >>> " + pair.getValue());
                }

            }
        }
        LOGGER.info("//////////////////////VARIABLES/////////////////");
        LOGGER.info(delegateExecution.getVariables().toString());
        LOGGER.info("//////////////////////VARIABLES/////////////////");

        em.getTransaction().commit();

        removeAllData(delegateExecution);


    }

    private void removeAllData(DelegateExecution del) {
        del.removeVariable("networkDisk");
        del.removeVariable("systemItem");
        del.removeVariable("papper");
        del.removeVariable("electronic");
        del.removeVariable("papperCheck");
    }
}
