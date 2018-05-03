package com.teamwork.example.pojo.response;

import com.google.gson.annotations.SerializedName;
import com.teamwork.example.model.TWPerson;

import org.parceler.Parcel;

import java.util.List;


@Parcel
public class PeopleResponsePojo extends ExampleResponsePojo{

    @SerializedName("people")
    List<TWPerson> people;

    public List<TWPerson> getPeople() {
        return people;
    }

    public void setPeople(List<TWPerson> people) {
        this.people = people;
    }
}
