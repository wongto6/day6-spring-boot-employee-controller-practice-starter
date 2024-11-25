package com.oocl.springbootemployee.repository;

import com.oocl.springbootemployee.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    private List<Company> companyList = new ArrayList<Company>();

    public CompanyRepository() {
        companyList.add(new Company(1, "OOCL"));
        companyList.add(new Company(2, "COSCO"));
        companyList.add(new Company(3, "Ms"));
        companyList.add(new Company(4, "Apple"));
    }

    public List<Company> getAllCompanies() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

}
