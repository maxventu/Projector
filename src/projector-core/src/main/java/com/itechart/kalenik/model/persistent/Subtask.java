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
@Table(name = "subtask")
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Type(type="text")
    private String description;

    @NotNull
    @Column(name = "open_date", nullable = false)
    private Date openDate;

    @NotNull
    @Column(name = "estimated_hours", nullable = false)
    private Integer estimatedHours;

    @Column(name = "closing_date")
    private Date closingDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subtask_type_id")
    private SubtaskType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by_id")
    private Employee assignedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solver_id")
    private Employee solver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lockingSubtask")
    private List<SubtaskLock> lockingList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lockedBySubtask")
    private List<SubtaskLock> lockedByList;
}
