package com.teamwork.example.pojo.response;

import com.google.gson.annotations.SerializedName;
import com.teamwork.example.model.TWCompany;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class CompaniesResponsePojo extends ExampleResponsePojo{

    @SerializedName("companies")
    List<TWCompany> companies;

    public List<TWCompany> getCompanies() {
        return companies;
    }

    public void setCompanies(List<TWCompany> companies) {
        this.companies = companies;
    }
}
