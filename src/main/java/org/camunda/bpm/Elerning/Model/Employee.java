package org.camunda.bpm.Elerning.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@NamedQueries({})
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String department;
    private String userName;


    public Employee(String department, String userName) {
        super( );
        this.department = department;
        this.userName = userName;
    }
    public Employee( ) {
        super();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
