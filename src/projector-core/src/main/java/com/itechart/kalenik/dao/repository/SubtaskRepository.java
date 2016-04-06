package com.itechart.kalenik.dao.repository;

import com.itechart.kalenik.model.persistent.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
}
