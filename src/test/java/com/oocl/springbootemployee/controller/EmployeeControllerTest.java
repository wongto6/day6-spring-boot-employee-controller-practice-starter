package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.Gender;
import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        String expectedJsonList = jsonList.write(expectedEmployees).getJson();

        //When
        //Then
        String resultJson = client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(resultJson).isEqualTo(expectedJsonList);
    }

    @Test
    void should_return_employee_when_get_by_id_given_id() throws Exception {
        //Given
        int givenEmployeeId = 1;
        Employee expectedEmployee = employeeRepository.getEmployeeById(givenEmployeeId);
        String expectedJson = json.write(expectedEmployee).getJson();

        //When
        //Then
        String resultJson = client.perform(MockMvcRequestBuilders.get("/employees/" + givenEmployeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(resultJson).isEqualTo(expectedJson);
    }

    @Test
    void should_return_male_when_get_by_gender_given_male() throws Exception {

        //Given
        List<Employee> expectedEmployees = employeeRepository.getEmployeesByGender(Gender.M);
        String expectedJsonList = jsonList.write(expectedEmployees).getJson();

        //When
        //Then

        String resultJson = client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("gender", Gender.M.name()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(resultJson).isEqualTo(expectedJsonList);
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
