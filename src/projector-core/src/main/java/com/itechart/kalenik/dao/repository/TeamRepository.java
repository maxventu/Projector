package com.itechart.kalenik.dao.repository;

import com.itechart.kalenik.model.persistent.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
