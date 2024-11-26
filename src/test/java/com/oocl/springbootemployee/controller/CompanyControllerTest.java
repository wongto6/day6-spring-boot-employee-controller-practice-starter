package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.entity.Company;
import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.repository.CompanyRepository;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CompanyControllerTest {

    @Autowired
    private MockMvc client;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    JacksonTester<List<Company>> companyJacksonTesterList;
    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void setup() {
        companyRepository.setCompanyList(new ArrayList<>());
        companyRepository.getAllCompanies().add(new Company(1, "OOCL"));
        companyRepository.getAllCompanies().add(new Company(2, "COSCO"));
        companyRepository.getAllCompanies().add(new Company(3, "Ms"));
        companyRepository.getAllCompanies().add(new Company(4, "Apple"));
    }

    @Test
    void should_return_employees_when_get_all_given_employees() throws Exception {
        //Given
        List<Company> expectedCompanies = companyRepository.getAllCompanies();

        //When
        //Then
        String resultJson = client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(companyJacksonTesterList.parse(resultJson).getObject())
                .usingRecursiveComparison()
                .isEqualTo(expectedCompanies);
    }

}
