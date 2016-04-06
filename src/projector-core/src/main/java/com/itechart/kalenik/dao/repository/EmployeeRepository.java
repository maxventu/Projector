package com.itechart.kalenik.dao.repository;

import com.itechart.kalenik.model.persistent.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByUsername(String username);
    Employee findById(Long id);
}
