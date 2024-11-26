package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.Gender;
import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class EmployeeControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    JacksonTester<Employee> employeeJacksonTester;
    @Autowired
    JacksonTester<List<Employee>> employeeJacksonTesterList;

    @BeforeEach
    void setup() {
        employeeRepository.setEmployees(new ArrayList<>());
        employeeRepository.getEmployees().add(new Employee(1, "Tony", 22, Gender.M, 500.0));
        employeeRepository.getEmployees().add(new Employee(2, "Johnson", 22, Gender.M, 500.0));
        employeeRepository.getEmployees().add(new Employee(3, "Angus", 22, Gender.M, 500.0));
        employeeRepository.getEmployees().add(new Employee(4, "Emily", 22, Gender.F, 500.0));
        employeeRepository.getEmployees().add(new Employee(5, "Emily", 22, Gender.F, 500.0));
        employeeRepository.getEmployees().add(new Employee(6, "Emily", 22, Gender.F, 500.0));
    }

    @Test
    void should_return_employees_when_get_all_given_employees() throws Exception {
        //Given
        List<Employee> expectedEmployees = employeeRepository.getEmployees();

        //When
        //Then
        String resultJson = client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(employeeJacksonTesterList.parse(resultJson).getObject())
                .usingRecursiveComparison()
                .isEqualTo(expectedEmployees);
    }

    @Test
    void should_return_employee_when_get_by_id_given_id() throws Exception {
        //Given
        int givenEmployeeId = 1;
        Employee expectedEmployee = employeeRepository.getEmployeeById(givenEmployeeId);

        //When
        //Then
        String resultJson = client.perform(MockMvcRequestBuilders.get("/employees/" + givenEmployeeId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(employeeJacksonTester.parseObject(resultJson)).isEqualTo(expectedEmployee);
    }

    @Test
    void should_return_male_when_get_by_gender_given_male() throws Exception {

        //Given
        List<Employee> expectedEmployees = employeeRepository.getEmployeesByGender(Gender.M);
        String expectedJsonList = employeeJacksonTesterList.write(expectedEmployees).getJson();

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
    void should_return_female_when_get_by_gender_given_female() throws Exception {

        //Given
        List<Employee> expectedEmployees = employeeRepository.getEmployeesByGender(Gender.F);
        String expectedJsonList = employeeJacksonTesterList.write(expectedEmployees).getJson();

        //When
        //Then

        String resultJson = client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("gender", Gender.F.name()))
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

    @Test
    void should_return_id_when_update_employee_given_employee_age_salary() throws Exception {

        //Given
        String updateJson = "{\n" +
                "        \"id\": 1,\n" +
                "        \"age\": 23,\n" +
                "        \"salary\": 600.0\n" +
                "    }";


        //When
        //Then
        client.perform(MockMvcRequestBuilders.put("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tony"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value("23"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value(Gender.M.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value("600.0"))
        ;

    }

    @Test
    void should_return_void_when_delete_employee_by_id_given_employee_id() throws Exception {

        //Given
        int employeeId = 1;
        //When
        //Then
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}", employeeId)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
        ;

        assertThrows(RuntimeException.class, () -> employeeRepository.getEmployeeById(employeeId));


    }

    @Test
    void should_return_employees_by_page_when_get_by_page_given_page2_size3() throws Exception {

        //Given
        List<Employee> expectedEmployees = employeeRepository.getEmployeesByPage(2, 3);
        String expectedJsonList = employeeJacksonTesterList.write(expectedEmployees).getJson();

        //When
        //Then

        String resultJson = client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("page", "2").param("size", "3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(resultJson).isEqualTo(expectedJsonList);
    }

    @Test
    void should_return_employees_by_page_when_get_by_page_given_page1_size5() throws Exception {

        //Given
        List<Employee> expectedEmployees = employeeRepository.getEmployeesByPage(2, 3);
        String expectedJsonList = employeeJacksonTesterList.write(expectedEmployees).getJson();

        //When
        //Then

        String resultJson = client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("page", "2").param("size", "3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(resultJson).isEqualTo(expectedJsonList);
    }

}
