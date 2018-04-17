package org.camunda.bpm.Elerning.Services;

import jdk.nashorn.internal.parser.JSONParser;
import org.camunda.bpm.Elerning.Model.Document;
import org.camunda.bpm.Elerning.Model.ElectronicData;
import org.camunda.bpm.Elerning.Model.ITDepartment;
import org.camunda.bpm.Elerning.Model.Supplier;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.util.json.JSONArray;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ViewDataService implements JavaDelegate {
    private final static Logger LOGGER = Logger.getLogger("DocumentsService");

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // todo zatim mam tenat,files from IT ,
        // todo zkusit to poslat rovnou a ne jen string !!!!!
        EntityManagerFactory ef = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = ef.createEntityManager();
        Query queryDocuments = em.createQuery("select e from Document e where e.tenant = :id");
        queryDocuments.setParameter("id", delegateExecution.getVariable("tenant"));
//        queryDocuments.setParameter("id", "Harumaph");
        List<Document> resultDocuments = queryDocuments.getResultList();

        Query queryElektronicData = em.createQuery("select e from ElectronicData e where e.tenant = :id");
        queryElektronicData.setParameter("id", delegateExecution.getVariable("tenant"));
//        queryElektronicData.setParameter("id", "Harumaph");
        List<ElectronicData> resultITsysetms = queryElektronicData.getResultList();

        List<ITDepartment> itDepartmentList = new ArrayList<>();
        for (ElectronicData el : resultITsysetms) {
            Query queryITdepartment = em.createQuery("select e from ITDepartment e where e.name = :name");
            queryITdepartment.setParameter("name",el.getITSystem());
            itDepartmentList.add((ITDepartment) queryITdepartment.getSingleResult());
        }

//        for (ElectronicData el:resultITsysetms) {
//            em.createQuery("select e from ITDepartment e where e.name = :name");
//        }


        JSONArray jsonArrayDocuments = createJsonDocuments(resultDocuments);
        JSONArray jsonArrayITsystems = createJsonITsystems(itDepartmentList);

        delegateExecution.setVariable("documents", jsonArrayDocuments.toString());
        delegateExecution.setVariable("itSystems", jsonArrayITsystems.toString());
//        tady musim spojit vyzit vechny uzivatele se stejnim tenant a provazat s mou employee a vypsa
        LOGGER.info("//////////////////////VARIABLES/////////////////");
        LOGGER.info(delegateExecution.getVariables().toString());
        LOGGER.info("//////////////////////VARIABLES-DOCUMENTS/////////////////");
        LOGGER.info(delegateExecution.getVariable("documents").toString());
//        if(delegateExecution.getVariable("IT_Gudeline")!=null){
//            LOGGER.info(delegateExecution.getVariable("IT_Gudeline").toString());
//        }else LOGGER.info("NULL POINTER");
        for (Document doc : resultDocuments) {
            LOGGER.info(doc.toString());
            LOGGER.info("///SUPP///");
            if (doc.getSupplier() != null) {
                LOGGER.info(doc.getSupplier().toString());
            }
        }
        LOGGER.info("///IT ELDATA///");
        for (ElectronicData el : resultITsysetms) {
            LOGGER.info(el.toString());
        }


        LOGGER.info("///IT Systems///");
        for (ITDepartment it : itDepartmentList) {
            LOGGER.info(it.toString());
        }
        LOGGER.info("//////////////////////VARIABLES/////////////////");
    }


    private JSONArray createJsonDocuments(List<Document> documentList) {
        JSONArray jsonArray = new JSONArray();
        for (Document doc : documentList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", doc.getName());
            jsonObject.put("url", doc.getUrl());
            jsonObject.put("type", doc.getType());
            if (doc.getSupplier() != null) {
                jsonObject.put("supplier", parserSupplier(doc.getSupplier()));
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    private JSONArray createJsonITsystems(List<ITDepartment> itDepartments){
        JSONArray jsonArray = new JSONArray();
        for (ITDepartment it:itDepartments) {
            JSONObject jso = new JSONObject();
            jso.put("name",it.getName());
            jso.put("description",it.getDescription());
            jso.put("url",it.getUrl());
        jsonArray.put(jso);
        }
        return jsonArray;
    }

    private JSONObject parserSupplier(Supplier supplier) {
        JSONObject js = new JSONObject();
        js.put("name", supplier.getName());
        js.put("itsystem", supplier.getItSystem());
        js.put("description", supplier.getDescription());
        return js;
    }
}
