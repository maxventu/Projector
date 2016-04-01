package com.itechart.kalenik.dao.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "due_date")
    private Date dueDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "project")
    private Team team;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Task> tasks;
}
