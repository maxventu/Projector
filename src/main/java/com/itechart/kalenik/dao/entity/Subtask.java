package com.itechart.kalenik.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "subtask")
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @Column(name = "open_date")
    private Date openDate;

    @Column(name = "estimation_time_hours")
    private Integer estimatedHours;

    @Column(name = "closing_date")
    private Date closingDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subtask_type_id",
            referencedColumnName = "id")
    private SubtaskType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by_id",
            referencedColumnName = "id")
    private Employee assignedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solver_id",
            referencedColumnName = "id")
    private Employee solver;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lockingSubtask")
    private List<SubtaskLock> lockingList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lockedBySubtask")
    private List<SubtaskLock> lockedByList;
}
