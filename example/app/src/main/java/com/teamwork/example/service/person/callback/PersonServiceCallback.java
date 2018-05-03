package com.teamwork.example.service.person.callback;

import com.teamwork.example.pojo.response.PeopleResponsePojo;
import com.teamwork.example.util.EnumUtil;

public interface PersonServiceCallback {

    void onPersonRequestError(EnumUtil.RESPONSE_CODE error);

    void onListPeopleResponse(PeopleResponsePojo response);
}
