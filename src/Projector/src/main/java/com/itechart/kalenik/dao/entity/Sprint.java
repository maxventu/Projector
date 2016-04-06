package com.itechart.kalenik.dao.entity;

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
@Table(name = "sprint")
public class Sprint {
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

    @NotNull
    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id",
            referencedColumnName = "id")
    private Task task;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sprint")
    private List<Subtask> subtasks;
}
