package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.Gender;
import com.oocl.springbootemployee.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private List<Employee> employees = new ArrayList<Employee>();

    public EmployeeRepository(){
        employees.add(new Employee(1, "Tony", 22,Gender.M, 500.0));
        employees.add(new Employee(2, "Johnson", 22,Gender.M, 500.0));
        employees.add(new Employee(3, "Angus", 22,Gender.M, 500.0));
        employees.add(new Employee(4, "Emily", 22,Gender.F, 500.0));
    }

    public List<Employee> getEmployees() {
        return employees;
    }

}
