package com.itechart.kalenik.dao.repository;

import com.itechart.kalenik.model.persistent.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
