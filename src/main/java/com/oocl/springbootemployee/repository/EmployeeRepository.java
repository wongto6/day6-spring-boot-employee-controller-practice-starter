package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.Gender;
import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.entity.UpdateAgeSalaryById;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private List<Employee> employees = new ArrayList<Employee>();

    public EmployeeRepository() {
        employees.add(new Employee(1, "Tony", 22, Gender.M, 500.0));
        employees.add(new Employee(2, "Johnson", 22, Gender.M, 500.0));
        employees.add(new Employee(3, "Angus", 22, Gender.M, 500.0));
        employees.add(new Employee(4, "Emily", 22, Gender.F, 500.0));
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee getEmployeeById(int id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst()
                .orElseThrow();
    }

    public List<Employee> getEmployeesByGender(Gender gender) {
        return employees.stream()
                .filter(employee -> employee.getGender() == gender)
                .toList();
    }

    public Employee createEmployee(Employee employee) {
        employee.setId(employees.size() + 1);
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployeeAgeSalaryById(UpdateAgeSalaryById updateAgeSalaryById) {
        Employee employee = getEmployeeById(updateAgeSalaryById.getId());
        employee.setAge(updateAgeSalaryById.getAge());
        employee.setSalary(updateAgeSalaryById.getSalary());
        return employee;
    }

    public void deleteEmployee(int id) {
        Employee employee = getEmployeeById(id);
        employees.remove(employee);
    }
}
