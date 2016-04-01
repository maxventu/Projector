package com.itechart.kalenik.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String name;
    private String surname;
    private String email;
    @JsonIgnore
    private String password;
    private Date deleted;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<EmployeeInTeam> teams;

    @OneToMany(mappedBy = "solver", fetch = FetchType.LAZY)
    private List<Subtask> assignedOnMeTasks;

    @OneToMany(mappedBy = "assignedBy", fetch = FetchType.LAZY)
    private List<Subtask> assignedByMeTasks;
}
