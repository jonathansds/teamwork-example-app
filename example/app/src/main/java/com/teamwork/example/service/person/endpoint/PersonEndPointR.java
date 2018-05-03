package com.teamwork.example.service.person.endpoint;

import com.teamwork.example.pojo.response.PeopleResponsePojo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PersonEndPointR {

    @GET("people.json")
    Call<PeopleResponsePojo> listPeople(@Query("page") int pageCursor);
}
