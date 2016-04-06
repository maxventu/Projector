package com.itechart.kalenik.model.persistent;

import lombok.Getter;
import lombok.Setter;
import com.itechart.security.model.persistent.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "employee_team")
public class EmployeeInTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_role_id")
    private EmployeeRoleInTeam role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;
}
