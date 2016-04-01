package com.itechart.kalenik.dao.config.service;

import com.itechart.kalenik.dao.config.PersistenceJPAConfig;
import com.itechart.kalenik.dao.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeServiceConfig {

    @Bean
    public EmployeeService employeeService(){
        return new EmployeeService();
    }


}
