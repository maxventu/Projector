package com.itechart.kalenik.dao.repository;

import com.itechart.kalenik.model.persistent.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
