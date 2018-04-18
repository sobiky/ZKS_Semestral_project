package org.camunda.bpm.Elerning.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table
public class PapperData implements Serializable {
    @Id
    @GeneratedValue
    private Integer ID_PapperData;
    private String tenant;
    private String type;
    private String place;

    public PapperData(String tenant, String type, String place) {
        super();
        this.tenant = tenant;
        this.type = type;
        this.place = place;
    }
    public PapperData(){
        super();
    }

    public Integer getID_PapperData() {
        return ID_PapperData;
    }

    public void setID_PapperData(Integer ID_PapperData) {
        this.ID_PapperData = ID_PapperData;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
