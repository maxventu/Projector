package com.itechart.kalenik.config.service;

import com.itechart.kalenik.config.PersistenceJPAConfig;
import com.itechart.kalenik.service.EmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeServiceConfig {

    @Bean
    public EmployeeService employeeService(){
        return new EmployeeService();
    }


}
