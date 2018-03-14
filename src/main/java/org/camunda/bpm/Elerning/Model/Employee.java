package org.camunda.bpm.Elerning.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@NamedQueries({})
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer employee_Id;
    private String department;
    private String userName;
    private String tenant;


    public Employee(String department, String userName, String tenant) {
        super();
        this.department = department;
        this.userName = userName;
        this.tenant = tenant;
    }

    public Employee() {
        super();
    }

    public Integer getEmloyeeId() {
        return employee_Id;
    }

    public void setEmloyeeId(Integer employee_Id) {
        this.employee_Id = employee_Id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
