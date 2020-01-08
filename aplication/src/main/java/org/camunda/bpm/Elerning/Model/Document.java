package org.camunda.bpm.Elerning.Model;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;

@Entity
@Table
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer documentId;
    private String name;
    private String tenant;
    private String url;
    private String type;
    private String hashcode;

    @ManyToOne
    @JoinColumn(name="supplierId")
    private Supplier supplier;

    @Lob
    private byte[] binaryFile;

    public Document(String name, String tenant, String url, String type, Supplier supplier, byte[] binaryFile,String hashcode) {
        super();
        this.name = name;
        this.tenant = tenant;
        this.url = url;
        this.type = type;
        this.supplier = supplier;
        this.binaryFile = binaryFile;
        this.hashcode = hashcode;
    }

    public Document() {
        super();
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBinaryFile() {
        return binaryFile;
    }

    public void setBinaryFile(byte[] binaryFile) {
        this.binaryFile = binaryFile;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    @Override
    public String toString() {

        return "Document{" +
                "documentId=" + documentId +
                ", name='" + name + '\'' +
                ", tenant='" + tenant + '\'' +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", supplier=" + supplier +
                ", hashcode=" + hashcode +
                ", binaryFile=" + binaryFile +
                '}';
    }
}
