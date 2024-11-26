package com.oocl.springbootemployee.entity;

import java.util.Objects;

public class Company {

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Company(int companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    private int companyId;

    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return companyId == company.companyId && Objects.equals(companyName, company.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyName);
    }
}
