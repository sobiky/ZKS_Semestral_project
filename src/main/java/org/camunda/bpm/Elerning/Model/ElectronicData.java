package org.camunda.bpm.Elerning.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table
public class ElectronicData implements Serializable{
    @Id
    @GeneratedValue
    private Integer ID_ElectronicData;
    private String type;
    private String ITSystem;
    private String NetworkDisk;

    public ElectronicData(String type, String ITSystem, String networkDisk) {
        super();
        this.type = type;
        this.ITSystem = ITSystem;
        NetworkDisk = networkDisk;
    }
    public ElectronicData(){
        super();
    }

    public Integer getID_ElectronicData() {
        return ID_ElectronicData;
    }

    public void setID_ElectronicData(Integer ID_ElectronicData) {
        this.ID_ElectronicData = ID_ElectronicData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getITSystem() {
        return ITSystem;
    }

    public void setITSystem(String ITSystem) {
        this.ITSystem = ITSystem;
    }

    public String getNetworkDisk() {
        return NetworkDisk;
    }

    public void setNetworkDisk(String networkDisk) {
        NetworkDisk = networkDisk;
    }
}
