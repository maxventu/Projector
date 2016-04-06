package com.itechart.kalenik.model.persistent;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Type(type="text")
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
