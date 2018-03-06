package org.camunda.bpm.Elerning.Services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
//        LOGGER.info("//////////////////////VARIABLES/////////////////");
//        LOGGER.info(delegateExecution.getVariables().toString());
//        if(delegateExecution.getVariable("IT_Gudeline")!=null){
//            LOGGER.info(delegateExecution.getVariable("IT_Gudeline").toString());
//        }else LOGGER.info("NULL POINTER");
//        LOGGER.info("//////////////////////VARIABLES/////////////////");
    }
}
