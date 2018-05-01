package org.camunda.bpm.Elerning.Services;


import org.camunda.bpm.Elerning.Model.Document;
import org.camunda.bpm.Elerning.Model.Supplier;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.ByteArrayInputStream;

import java.util.logging.Logger;


public class SupplierService implements JavaDelegate {
    private final static Logger LOGGER = Logger.getLogger("SupplierService");

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        EntityManagerFactory ef = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = ef.createEntityManager();
        em.getTransaction().begin();

        LOGGER.info("//////////////////////VARIABLES/////////////////");
        LOGGER.info(delegateExecution.getVariables().toString());
        LOGGER.info("//////////////////////VARIABLES/////////////////");
        TypedValue suppliersList = delegateExecution.getVariableTyped("suppliersList");


        JSONParser parser = new JSONParser();
        Object obj = parser.parse(suppliersList.getValue().toString());
        JSONArray array = (JSONArray) obj;

        LOGGER.info("//////////////////////JSONPARSER/////////////////");
        LOGGER.info(array.toJSONString());
        LOGGER.info(array.toString());
        LOGGER.info("//////////////////////JSONPARSER-ARRAY/////////////////");
//        List<Document> documents = new LinkedList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject obje = (JSONObject) array.get(i);

            Supplier supplier = new Supplier();
            LOGGER.info((String) obje.get("supplierITSystem"));
            supplier.setItSystem((String) obje.get("supplierITSystem"));
            supplier.setTenant(delegateExecution.getVariable("tenant").toString());
            supplier.setName((String) obje.get("supplierName"));
            supplier.setDescription((String) obje.get("supplierDescription"));

            JSONArray arraySingleDocument = (JSONArray) obje.get("supplierDataUrl");
            for (int j = 0; j < arraySingleDocument.size(); j++) {
                Document doc = new Document();
                JSONObject ob = (JSONObject) arraySingleDocument.get(j);
                doc.setHashcode((String) ob.get("hashcode"));
                doc.setUrl((String) ob.get("url"));
                JSONArray namelist = (JSONArray) obje.get("supplierFileName");
                JSONObject name = (JSONObject) namelist.get(j);
                doc.setName((String) name.get("name"));
                doc.setType((String) name.get("type"));
                doc.setTenant(delegateExecution.getVariable("tenant").toString());

                ByteArrayInputStream bais =
                        (ByteArrayInputStream) delegateExecution.getVariable("Files" + doc.getHashcode());

                byte[] bytes = new byte[bais.available()];
                bais.read(bytes);
                doc.setBinaryFile(bytes);
                doc.setSupplier(supplier);
                em.persist(doc);
                supplier.addDocuments(doc);


            }
            em.persist(supplier);
        }
            em.getTransaction().commit();
        LOGGER.info("//////////////////////JSONPARSER-ARRAY/////////////////");
        delegateExecution.removeVariable("supplierCheckbox");
    }
}
