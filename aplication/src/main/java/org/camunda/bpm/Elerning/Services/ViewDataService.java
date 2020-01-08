package org.camunda.bpm.Elerning.Services;


import org.camunda.bpm.Elerning.Model.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.util.json.JSONArray;
import org.camunda.bpm.engine.impl.util.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class ViewDataService implements JavaDelegate {
    private final static Logger LOGGER = Logger.getLogger("DocumentsService");

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // todo zatim mam tenat,files from IT ,
        // todo vyselektit i papirove data a zobrazit
        EntityManagerFactory ef = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = ef.createEntityManager();
        Query queryDocuments = em.createQuery("select e from Document e where e.tenant = :tenant");
        queryDocuments.setParameter("tenant", delegateExecution.getVariable("tenant"));
//        queryDocuments.setParameter("id", "Harumaph");
        List<Document> resultDocuments = queryDocuments.getResultList();


        Query queryElektronicData = em.createQuery("select e from ElectronicData e where e.tenant = :tenant");
        queryElektronicData.setParameter("tenant", delegateExecution.getVariable("tenant"));
//        queryElektronicData.setParameter("id", "Harumaph");
        List<ElectronicData> resultITsysetms = queryElektronicData.getResultList();

        Query queryPapper = em.createQuery("select e from PapperData e where e.tenant = :tenant");
        queryPapper.setParameter("tenant", delegateExecution.getVariable("tenant"));
        List<PapperData> resultPappers = queryPapper.getResultList();


        List<ITSystem> itSystemList = new ArrayList<>();
        List<Pair> listNetworkDisk = new ArrayList<>();
        for (ElectronicData el : resultITsysetms) {
            if (el.getNetworkDisk() != null) {
                Pair pair = new Pair(el.getITSystem(), el.getNetworkDisk());
                listNetworkDisk.add(pair);
            }

            Query queryITdepartment = em.createQuery("select e from ITSystem e where e.name = :name");
            queryITdepartment.setParameter("name", el.getITSystem());
            ITSystem itdepatment = (ITSystem) queryITdepartment.getSingleResult();
            boolean checkExist = false;
            for (ITSystem it : itSystemList) {
                if (Objects.equals(it.getName(), itdepatment.getName())) {
                    checkExist = true;
                }
            }
            if (!checkExist) {
                itSystemList.add(itdepatment);
            }

        }




        JSONArray jsonArrayDocuments = createJsonDocuments(resultDocuments);
        JSONArray jsonArrayITsystems = createJsonITsystems(itSystemList, listNetworkDisk);
        JSONArray jsonArrayPapper = createJsonPapper(resultPappers);
        JSONArray jsonArrayAllUsers = createJsonAllUsers(em, delegateExecution);

        delegateExecution.setVariable("documents", jsonArrayDocuments.toString());
        delegateExecution.setVariable("itSystems", jsonArrayITsystems.toString());
        delegateExecution.setVariable("listPappers", jsonArrayPapper.toString());
        delegateExecution.setVariable("listAllUsers", jsonArrayAllUsers.toString());



        for (Document doc : resultDocuments) {
//            LOGGER.info(doc.toString());
//            LOGGER.info("///SUPP///");
            if (doc.getSupplier() != null) {
//                LOGGER.info(doc.getSupplier().toString());
            }
        }
//        LOGGER.info("///IT ELDATA///");
        for (ElectronicData el : resultITsysetms) {
//            LOGGER.info(el.toString());
        }


//        LOGGER.info("///IT Systems///");
        for (ITSystem it : itSystemList) {
//            LOGGER.info(it.toString());
        }
//        LOGGER.info("//////////////////////VARIABLES/////////////////");
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

    private JSONArray createJsonITsystems(List<ITSystem> itSystems, List<Pair> electronic) {
        JSONArray jsonArray = new JSONArray();
        for (ITSystem it : itSystems) {
            JSONObject jso = new JSONObject();
            jso.put("name", it.getName());
            jso.put("description", it.getDescription());
            jso.put("url", it.getUrl());
            JSONArray arrayAdmin = new JSONArray();
            for (AdministratorITSystem adm : it.getAdmin()) {
                JSONObject admin = new JSONObject();
                admin.put("name", adm.getAdminName());
                admin.put("email", adm.getEmail());
                admin.put("phone", adm.getPhone());
                arrayAdmin.put(admin);
            }
            jso.put("arrayAdmins", arrayAdmin);

            JSONArray jsonArrayNetworkDisk = new JSONArray();
            for (Pair pair : electronic) {
                if (Objects.equals(pair.getKey(), it.getName())) {
                    JSONObject networkDisk = new JSONObject();
                    networkDisk.put("networkDisk", pair.getValue());
                    jsonArrayNetworkDisk.put(networkDisk);
                }
            }
            jso.put("arrayNetworkDisks", jsonArrayNetworkDisk);
            jsonArray.put(jso);
        }
        return jsonArray;
    }

    private JSONArray createJsonPapper(List<PapperData> resultPappers) {
        JSONArray jsonArray = new JSONArray();
        for (PapperData papper : resultPappers) {
            JSONObject jso = new JSONObject();
            jso.put("place", papper.getPlace());
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

    private JSONArray createJsonAllUsers(EntityManager em, DelegateExecution delegateExecution) {

        List<User> userList = delegateExecution.getProcessEngineServices()
                .getIdentityService()
                .createUserQuery()
                .memberOfTenant((delegateExecution.getVariable("tenant").toString())).list();
        LOGGER.info("//////////////////////////USERLIST//////////////////////////");
        LOGGER.info(Arrays.toString(userList.toArray()));
        JSONArray array = new JSONArray();
        for (User usr : userList) {
//todo mam data tak je treba uz jen zobrazit
            LOGGER.info(usr.getId());
                JSONObject jso = new JSONObject();
                jso.put("firstName", usr.getFirstName());
                jso.put("lastName", usr.getLastName());
                jso.put("email", usr.getEmail());



            Query query = em.createQuery("select e from Employee e where e.userName = :name");
            query.setParameter("name", usr.getId());
            LOGGER.info(Arrays.toString(query.getResultList().toArray()));
            if (!Objects.equals(Arrays.toString(query.getResultList().toArray()), "[]")) {
                Employee emp = (Employee) query.getSingleResult();
                LOGGER.info(emp.toString());
                if (emp.getDepartment() != null) {
                    jso.put("department", emp.getDepartment());
                }
            }
                array.put(jso);
        }
        return array;
    }
}
