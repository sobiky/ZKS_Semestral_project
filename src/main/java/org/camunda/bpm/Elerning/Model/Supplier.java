package org.camunda.bpm.Elerning.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Supplier implements Serializable {
    @Id
    @GeneratedValue
    private Integer supplierId;
    private String name;
    private String description;
    private String ItSystem;
    private String tenant;

    @OneToMany(targetEntity = org.camunda.bpm.Elerning.Model.Document.class,
            mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<Document> documents;

    public Supplier(String name, String description, String itSystem, Set<Document> documents,String tenant) {
        super();
        this.documents = new HashSet<>();
        this.name = name;
        this.description = description;
        this.ItSystem = itSystem;
        this.documents = documents;
        this.tenant = tenant;
    }

    public Supplier() {
        super();
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItSystem() {
        return ItSystem;
    }

    public void setItSystem(String itSystem) {
        ItSystem = itSystem;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public void addDocuments(Document document) {
        this.documents.add(document);
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }
    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
