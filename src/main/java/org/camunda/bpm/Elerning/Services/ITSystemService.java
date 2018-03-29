package org.camunda.bpm.Elerning.Services;

import org.camunda.bpm.Elerning.Config.Base64;
import org.camunda.bpm.Elerning.CreateNewUserForEmployee;
import org.camunda.bpm.Elerning.Model.Document;
import org.camunda.bpm.Elerning.Model.ITDepartment;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.logging.Logger;

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


        delegateExecution.setVariable("systemsList", Variables.objectValue(department.toArray())
                .serializationDataFormat(Variables.SerializationDataFormats.JSON).create());
        //todo tady je videt url objektu a data jeho
        LOGGER.info(String.valueOf(delegateExecution.getVariables()));
        TypedValue fileData = delegateExecution.getVariableTyped("camundaDataUrl");
        if (!Objects.equals(fileData.getValue().toString(), "[]")) {


            List<Map<String, String>> data = CreateNewUserForEmployee.PavelMagicParser(fileData);
            TypedValue fileName = delegateExecution.getVariableTyped("dataInputName");
            List<Map<String, String>> dataName = CreateNewUserForEmployee.PavelMagicParser(fileName);
            List<Document> documents = new LinkedList<>();

            //todo dodelat kontrolu zavorky i pro ostatni
            for (Map<String, String> aData : data) {
                String url = aData.get("url");
                if (aData.get("url").charAt(aData.get("url").length() - 1) == '}') {
                    url = aData.get("url").substring(0, aData.get("url").length() - 2);
                }
                Document document = new Document();
                document.setTenant(delegateExecution.getVariable("tenant").toString());
                document.setUrl(aData.get("url"));
                LOGGER.info("*********");
                LOGGER.info("name=" + aData.get("name"));
                LOGGER.info("url=" + url);
                documents.add(document);
            }
            int i = 0;
            for (Map<String, String> aData : dataName) {
                documents.get(i).setType(aData.get("type"));
                documents.get(i).setName(aData.get("name"));

                ByteArrayInputStream bais =
                        (ByteArrayInputStream) delegateExecution.getVariable(documents.get(i).getType()+"Files");

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


            em.getTransaction().begin();
            for (Document doc :documents) {
                em.persist(doc);
            }
            em.getTransaction().commit();
//             delegateExecution.getVariable(""+documents.get(0).getType()+"Files");
//            ByteArrayInputStream bais =
//                    (ByteArrayInputStream) delegateExecution.getVariable(documents.get(0).getType() + "Files");
//
//            byte[] bytes = new byte[bais.available()];
//            bais.read(bytes);

//            for (Byte bi:bytes) {
//                LOGGER.info(String.valueOf(bi));
//            }

//            bytes.
//            byte[] dataFile = Base64.getDecoder().decode(bytes);
//            try (OutputStream stream = new FileOutputStream("abcdef.pdf")) {
//                stream.write(bytes);
//                stream.flush();
//            }

//            String url = "http://localhost:8080" + documents.get(0).getUrl() + "/data";
//
//            URL obj = new URL(url);
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//            // optional default is GET
//            con.setRequestMethod("GET");
//
//            //add request header
//            con.setRequestProperty("User-Agent", "Mozilla/5.0");
//
//            int responseCode = con.getResponseCode();
//            System.out.println("\nSending 'GET' request to URL : " + url);
//            System.out.println("Response Code : " + responseCode);
//
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            //print result
//            LOGGER.info(response.toString());


        }
    }


}
