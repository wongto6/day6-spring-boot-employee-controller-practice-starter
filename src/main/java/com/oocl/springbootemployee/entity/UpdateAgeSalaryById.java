package com.oocl.springbootemployee.entity;

import com.oocl.springbootemployee.Gender;

public class UpdateAgeSalaryById {

    private int id;

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int age;
    private Double salary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
