package com.itechart.kalenik.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "employee_team")
public class EmployeeInTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id",
            referencedColumnName = "id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_role_id",
            referencedColumnName = "id")
    private EmployeeRoleInTeam role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id",
            referencedColumnName = "id")
    private Team team;
}
