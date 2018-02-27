package org.camunda.bpm.Elerning.Model;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;

@Entity
@Table
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Lob
    private File binaryFile;
    private String firm;//from camunda = tenat

    public Document(String name, File binaryFile, String firm) {
        this.name = name;
        this.binaryFile = binaryFile;
        this.firm = firm;
    }
    public Document(){
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getBinaryFile() {
        return binaryFile;
    }

    public void setBinaryFile(File binaryFile) {
        this.binaryFile = binaryFile;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }
}
