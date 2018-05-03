package com.teamwork.example.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

//Not being used but created in case I add more features into this app
@Parcel
public class TWCategory {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
