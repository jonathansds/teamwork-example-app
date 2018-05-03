package com.teamwork.example.service.company.endpoint;

import com.teamwork.example.pojo.response.CompaniesResponsePojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CompanyEndPointR {

    @GET("companies.json")
    Call<CompaniesResponsePojo> listCompanies(@Query("page") int pageCursor);
}
