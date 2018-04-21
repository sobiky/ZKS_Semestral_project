package org.camunda.bpm.Elerning.Services;


import org.camunda.bpm.Elerning.CreateNewUserForEmployee;
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
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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


//        Supplier supplier = new Supplier();
        //todo treba tohle udelat pro list supplieru a ne jen pro jednoho
//        supplier.setName(delegateExecution.getVariable("supplierName").toString());
//        supplier.setTenant(delegateExecution.getVariable("tenant").toString());
//        supplier.setDescription(delegateExecution.getVariable("supplierDescription").toString());
//        if (delegateExecution.getVariable("supplierITSystem") != null) {
//            supplier.setItSystem(delegateExecution.getVariable("supplierITSystem").toString());
//        }
        //todo je treba jeste udelat ukladani dokumentu supleireru  eeee
//        TypedValue fileData = delegateExecution.getVariableTyped("supplierDataUrl");
//        TypedValue fileName = delegateExecution.getVariableTyped("supplierFileName");
//        LOGGER.info("ALL DATA");
//        LOGGER.info(delegateExecution.getVariables().toString());
//        LOGGER.info("ALL DATA");

//        if (!Objects.equals(fileData.getValue().toString(), "[]")) {
//            List<Map<String, String>> dataName = CreateNewUserForEmployee.PavelMagicParser(fileName);
//            List<Map<String, String>> data = CreateNewUserForEmployee.PavelMagicParser(fileData);
//            LOGGER.info(data.toArray().toString());
//            LOGGER.info(dataName.toArray().toString());
//            List<Document> documents = new LinkedList<>();
//
//            documents = fileUploadToDatabase(delegateExecution, data, dataName);
//            for (Document doc : documents) {
//                doc.setSupplier(supplier);
//                em.persist(doc);
//                supplier.addDocuments(doc);
//            }
//

//            em.persist(supplier);
//            em.getTransaction().commit();
//        removeData(delegateExecution);
//        }

    }

//    private void removeData(DelegateExecution del) {
//        del.removeVariable("supplierName");
//        del.removeVariable("supplierDescription");
//        del.removeVariable("supplierITSystem");
//    }


    private List<Document> fileUploadToDatabase(DelegateExecution delegateExecution, List<Map<String, String>> data, List<Map<String, String>> dataName) throws IOException {
        List<Document> documents = new LinkedList<>();
        for (Map<String, String> aData : data) {
            LOGGER.info("DATAURL");
            LOGGER.info(aData.get("url"));
            LOGGER.info("/DATAURL");
            String url = aData.get("url");
            //todo dodelat kontrolu zavorky i pro ostatni
            if (aData.get("url").charAt(aData.get("url").length() - 1) == '}') {
                url = aData.get("url").substring(0, aData.get("url").length() - 2);
            }
            Document document = new Document();
            document.setTenant(delegateExecution.getVariable("tenant").toString());
            document.setUrl(url);
            document.setHashcode(aData.get("hashcode"));
            LOGGER.info("name=" + aData.get("name"));
            LOGGER.info("url=" + url);
            documents.add(document);
        }
        int i = 0;
        for (Map<String, String> aData : dataName) {
            documents.get(i).setType(aData.get("type"));
            String name = aData.get("name");
            if (aData.get("name").charAt(aData.get("name").length() - 1) == '}') {
                name = aData.get("name").substring(0, aData.get("name").length() - 2);
            }
            documents.get(i).setName(name);

            LOGGER.info("Files" + documents.get(i).getHashcode());
            ByteArrayInputStream bais =
                    (ByteArrayInputStream) delegateExecution.getVariable("Files" + documents.get(i).getHashcode());

            byte[] bytes = new byte[bais.available()];
            bais.read(bytes);
            documents.get(i).setBinaryFile(bytes);


            LOGGER.info("*********");
            LOGGER.info("type=" + aData.get("type"));
            LOGGER.info("name=" + aData.get("name"));
            i++;
        }

        for (Document doc : documents) {
            LOGGER.info("DATA CO ZATIM MAM");
            LOGGER.info(doc.toString());
        }
        return documents;
    }
}
