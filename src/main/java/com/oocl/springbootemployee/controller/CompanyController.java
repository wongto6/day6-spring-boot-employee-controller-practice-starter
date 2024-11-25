package com.oocl.springbootemployee.controller;

import com.oocl.springbootemployee.entity.Company;
import com.oocl.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping
    public List<Company> getAllCompany() {
        return companyRepository.getAllCompanies();
    }

}
