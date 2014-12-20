package com.gigaspaces.model;

import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * Created by kobi on 12/10/14.
 */
public class Company {
    private Integer companyId;
    private String CompanyName;
    private String CompanyCity;

    @SpaceId
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyCity() {
        return CompanyCity;
    }

    public void setCompanyCity(String companyCity) {
        CompanyCity = companyCity;
    }
}
