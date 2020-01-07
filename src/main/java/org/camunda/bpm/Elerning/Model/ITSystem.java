package org.camunda.bpm.Elerning.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class ITSystem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ITSystemId;
    private String name;
    private String url;
    private String description;
    private String tenant;

    @OneToMany(targetEntity = org.camunda.bpm.Elerning.Model.AdministratorITSystem.class,
            mappedBy = "system", cascade = CascadeType.ALL)
    private Set<AdministratorITSystem> admin;


    public ITSystem(String name, String url, String description, Set<AdministratorITSystem> admin, String tenant) {
        super();
        this.name = name;
        this.url = url;
        this.description = description;
        this.admin = admin;
        this.tenant = tenant;

    }

    public ITSystem() {
        super();
        admin = new HashSet<>();
    }

    public Integer getITDepartmentId() {
        return ITSystemId;
    }

    public void setITDepartmentId(Integer ITDepartmentId) {
        this.ITSystemId = ITDepartmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Set<AdministratorITSystem> getAdmin() {
        return admin;
    }


    public void addAdmin(AdministratorITSystem admin) {
        this.admin.add(admin);
    }


    public void setAdmin(Set<AdministratorITSystem> admin) {
        this.admin = admin;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
//todo lepsi vypis by to chtelo tedy
    @Override
    public String toString() {
        return "ITSystem{" +
                "ITSystemId=" + ITSystemId +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", tenant='" + tenant + '\'' +
                ", admin=" + printAdmin(admin) +
                '}';
    }
    private String printAdmin(Set<AdministratorITSystem> supplier){
        StringBuilder stringBuilder = new StringBuilder();
        for (AdministratorITSystem admin :supplier) {
            stringBuilder.append(admin.toString());
        }
        return stringBuilder.toString();
    }
}
