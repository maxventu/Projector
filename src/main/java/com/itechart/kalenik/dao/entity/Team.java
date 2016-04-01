package com.itechart.kalenik.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",
            referencedColumnName = "id")
    private List<EmployeeInTeam> employees;
}
