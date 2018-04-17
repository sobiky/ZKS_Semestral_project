package org.camunda.bpm.Elerning.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@NamedQueries({})
public class AdministratorITSystem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer adminId;
    private String adminName;
    private String email;
    private String phone;

    @ManyToOne
    @JoinColumn(name="ITDepartmentId")
    private ITDepartment system;

    public AdministratorITSystem(String adminName, String email, String phone) {
        super();
        this.adminName = adminName;
        this.email = email;
        this.phone = phone;
    }

    public AdministratorITSystem() {
        super();
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public ITDepartment getSystem() {
        return system;
    }

    public void setSystem(ITDepartment system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return "AdministratorITSystem{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
