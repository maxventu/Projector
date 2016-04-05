package com.itechart.kalenik.service;


import com.itechart.kalenik.dao.entity.Employee;
import com.itechart.kalenik.dao.repository.EmployeeRepository;
import com.itechart.kalenik.utils.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class EmployeeService {

    private static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    public EmployeeRepository employeeRepository;

    public String createEmployee(Employee employee){
        logger.debug("creating employee {} {} {}",employee.getUsername(),employee.getName(),employee.getSurname());
        employeeRepository.save(employee);
        return Const.SUCCESS_RESULT_ACTION;
    }

    @Transactional(readOnly = true)
    public Employee findByUsername(String username){
        logger.debug("finding employee with username {}",username);
        return employeeRepository.findByUsername(username);
    }

    public String deleteEmployee(String username){
        Employee employee = employeeRepository.findByUsername(username);
        return deleteEmployee(employee);
    }

    public String deleteEmployee(Integer id){
        Employee employee = employeeRepository.findById(id);
        return deleteEmployee(employee);
    }

    public String deleteEmployee(Employee employee){
        if(employee == null) return Const.FAILED_RESULT_ACTION;
        Date currentDate = new Date();
        employee.setDeleted(currentDate);
        employeeRepository.save(employee);
        return Const.SUCCESS_RESULT_ACTION;
    }
}
