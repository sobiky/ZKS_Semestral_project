package org.camunda.bpm.Elerning.Services;

import org.camunda.bpm.Elerning.CreateNewUserForEmployee;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
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

        //todo najist moznost jak pouzit POST methodu k tomu abych nahral soubor do promenenne
        //todo najit jak napsat a provazat script abych mohl pouzit Angulari post
        //todo vyresit problem s nahrati souboru najak posilat nazev souboru........
        EntityManagerFactory ef = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = ef.createEntityManager();
        TypedValue itDepartments = delegateExecution.getVariableTyped("camundaDataUrl");
        if (!Objects.equals(itDepartments.getValue().toString(), "[]")) {
            List<Map<String, String>> data = CreateNewUserForEmployee.PavelMagicParser(itDepartments);

            //todo mam data ktere chci poslat do databaze.
            // todo je treba je tam jeste poslat a udelat to tak aby to fungovalo paraelne (NEMAM TUSENI JAK TO BUDE FUNGOVAT :( :D)
            for (Map<String, String> item : data) {
                for (Map.Entry<String, String> pair : item.entrySet()) {
                    LOGGER.info("/" + pair.getKey() + " >>> " + pair.getValue());
                }

            }
        }
        LOGGER.info("//////////////////////VARIABLES/////////////////");
        LOGGER.info(delegateExecution.getVariables().toString());
//        if(delegateExecution.getVariable("IT_Gudeline")!=null){
//            LOGGER.info(delegateExecution.getVariable("IT_Gudeline").toString());
//        }else LOGGER.info("NULL POINTER");
        LOGGER.info("//////////////////////VARIABLES/////////////////");
    }

}
