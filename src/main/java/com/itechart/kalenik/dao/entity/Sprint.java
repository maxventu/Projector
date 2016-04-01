package com.itechart.kalenik.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sprint")
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Date creationDate;
    private Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id",
            referencedColumnName = "id")
    private Task task;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sprint")
    private List<Subtask> subtasks;
}
