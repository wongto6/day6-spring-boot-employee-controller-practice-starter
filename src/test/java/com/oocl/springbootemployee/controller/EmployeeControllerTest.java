package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void should_return_employees_when_get_all_given_employees() throws Exception {
        //Given
        List<Employee> expectedEmployees = employeeRepository.getEmployees();

        //When
        //Then
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(expectedEmployees.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(expectedEmployees.get(1).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(expectedEmployees.get(2).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].name").value(expectedEmployees.get(3).getName()))
        ;
    }

    @Test
    void should_return_employee_when_get_by_id_given_id() throws Exception {
        //Given
        int givenEmployeeId = 1;
        Employee expectedEmployee = employeeRepository.getEmployeeById(givenEmployeeId);

        //When
        //Then
        client.perform(MockMvcRequestBuilders.get("/employees/" + givenEmployeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedEmployee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedEmployee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(expectedEmployee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(expectedEmployee.getGender().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(expectedEmployee.getSalary()))
        ;
    }




}
