package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.Gender;
import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.json.JsonTestersAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class EmployeeControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    JacksonTester<Employee> json;
    @Autowired
    JacksonTester<List<Employee>> jsonList;


    @Test
    void should_return_employees_when_get_all_given_employees() throws Exception {
        //Given
        List<Employee> expectedEmployees = employeeRepository.getEmployees();


        //When
        //Then
        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(expectedEmployees.size())))
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

    @Test
    void should_return_male_when_get_by_gender_given_male() throws Exception {

        //Given
        List<Employee> expectedEmployees = employeeRepository.getEmployeesByGender(Gender.M);

        //When
        //Then
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("gender", Gender.M.name()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(expectedEmployees.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(expectedEmployees.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(expectedEmployees.get(1).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(expectedEmployees.get(2).getName()))
        ;

    }

    @Test
    void should_return_id_when_create_employee_given_employee() throws Exception {

        //Given
        String employeeJson = "{\n" +
                "        \"name\": \"Tony\",\n" +
                "        \"age\": 22,\n" +
                "        \"gender\": \"M\",\n" +
                "        \"salary\": 500.0\n" +
                "    }";

        //When
        //Then
        client.perform(MockMvcRequestBuilders.post("/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(employeeJson)
                        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tony"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("22"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(Gender.M.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("500.0"))
        ;

    }


}
