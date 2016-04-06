package com.itechart.kalenik.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "subtask_lock")
public class SubtaskLock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtask_locking_id")
    private Subtask lockingSubtask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtask_locked_by_id")
    private Subtask lockedBySubtask;
}
