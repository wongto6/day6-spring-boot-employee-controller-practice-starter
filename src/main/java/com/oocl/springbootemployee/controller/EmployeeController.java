package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.Gender;
import com.oocl.springbootemployee.entity.Employee;
import com.oocl.springbootemployee.entity.UpdateAgeSalaryVo;
import com.oocl.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.getEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeRepository.getEmployeeById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeeByGender(@RequestParam Gender gender) {
        return employeeRepository.getEmployeesByGender(gender);
    }

    @GetMapping(params = {"page", "size"})
    public List<Employee> employeesByPage(@RequestParam Integer page, @RequestParam Integer size) {
        return employeeRepository.getEmployeesByPage(page, size);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.createEmployee(employee);
    }

    @PutMapping()
    public Employee putEmployee(@RequestBody UpdateAgeSalaryVo updateAgeSalaryVo) {
        return employeeRepository.updateEmployeeAgeSalaryById(updateAgeSalaryVo);
    }

    @DeleteMapping(path="/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) {
        employeeRepository.deleteEmployee(id);
    }

}
