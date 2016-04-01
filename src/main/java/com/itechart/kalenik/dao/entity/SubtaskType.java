package com.itechart.kalenik.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "subtask_type")
public class SubtaskType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "subtask_type_id")
    private List<Subtask> subtasks;
}
