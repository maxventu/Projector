package com.itechart.kalenik.dao.repository;

import com.itechart.kalenik.dao.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByUsername(String username);
    Employee findById(Integer id);
}
