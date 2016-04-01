package com.itechart.kalenik;

import com.itechart.kalenik.dao.config.PersistenceJPAConfig;
import com.itechart.kalenik.dao.config.PropertiesConfig;
import com.itechart.kalenik.dao.config.service.EmployeeServiceConfig;
import com.itechart.kalenik.dao.entity.Employee;
import com.itechart.kalenik.dao.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(classes = PersistenceJPAConfig.class),
        @ContextConfiguration(classes = PropertiesConfig.class),
        @ContextConfiguration(classes = EmployeeServiceConfig.class)
})

@Transactional
public class HibernateTest{

    private static Logger logger = LoggerFactory.getLogger(HibernateTest.class);

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testCreateEmployee(){
        Employee employee = new Employee();
        employee.setUsername("theBestUser");
        employee.setName("Саша");
        employee.setSurname("Белый");
        employee.setPassword((new BCryptPasswordEncoder(4)).encode("1234567"));
        logger.debug("New employee username: {}, name: {}, surname: {}, password: {}",
                employee.getUsername(),
                employee.getName(),
                employee.getSurname(),
                employee.getPassword());

        String savingResult = employeeService.createEmployee(employee);
        logger.debug("result of saving: {}",savingResult);

        logger.debug("Employee after saving id: {}, username: {}, name: {}, surname: {}, password: {}",
                employee.getId(),
                employee.getUsername(),
                employee.getName(),
                employee.getSurname(),
                employee.getPassword());

        Employee savedEmployee = employeeService.findByUsername(employee.getUsername());
        logger.debug("Founded employee id: {}, username: {}, name: {}, surname: {}, password: {}",
                savedEmployee.getId(),
                savedEmployee.getUsername(),
                savedEmployee.getName(),
                savedEmployee.getSurname(),
                savedEmployee.getPassword());

        Integer id = savedEmployee.getId();
        String deletingResult = employeeService.deleteEmployee(id);
        logger.debug("deleting result: {}",deletingResult);
    }
}
