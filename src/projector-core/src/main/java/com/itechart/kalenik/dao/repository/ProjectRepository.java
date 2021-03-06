package com.itechart.kalenik.dao.repository;

import com.itechart.kalenik.model.persistent.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository  extends JpaRepository<Project, Long> {
}
