package com.teamwork.example.service.person.impl;

import android.support.annotation.NonNull;

import com.teamwork.example.pojo.response.PeopleResponsePojo;
import com.teamwork.example.service.ExampleServiceR;
import com.teamwork.example.service.person.callback.PersonServiceCallback;
import com.teamwork.example.service.person.contract.PersonService;
import com.teamwork.example.service.person.endpoint.PersonEndPointR;
import com.teamwork.example.util.EnumUtil;
import com.teamwork.example.util.FunctionUtil;

import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonServiceRetrofitImpl extends ExampleServiceR implements PersonService{
    private PersonServiceCallback callback;

    public PersonServiceRetrofitImpl(PersonServiceCallback callback){
        this.callback = callback;
    }

    @Override
    public void releaseCallback(){
        this.callback = null;
    }

    @Override
    protected void validateRequest() throws Exception{
        if(!FunctionUtil.isInternetAvailable()){
            if(callback != null)
                callback.onPersonRequestError(EnumUtil.RESPONSE_CODE.CONNECTION_ERROR);

            throw new UnknownHostException();
        }
    }

    //There's too much repeated code here. I need to have a look at how to improve this process
    @Override
    public void listPeople(int pageCursor) {
        try {
            validateRequest();

            Call<PeopleResponsePojo> call  = getRetrofit().create(PersonEndPointR.class).listPeople(pageCursor);

            call.enqueue(new Callback<PeopleResponsePojo>() {
                @Override
                public void onResponse(@NonNull Call<PeopleResponsePojo> call, @NonNull Response<PeopleResponsePojo> response) {
                    PeopleResponsePojo responsePeople;

                    //It's not safe to assume that a success http request is equivalent to success on the host
                    if(response.body() != null && response.code() == 200){
                        responsePeople = setPagination(response);
                        responsePeople.setCode(EnumUtil.RESPONSE_CODE.SUCCESS);

                    }else {
                        responsePeople = new PeopleResponsePojo();
                        responsePeople.setCode(EnumUtil.RESPONSE_CODE.fromIntCode(response.code()));
                    }

                    if(callback != null){
                        callback.onListPeopleResponse(responsePeople);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PeopleResponsePojo> call, @NonNull Throwable throwable) {
                    PeopleResponsePojo responsePeople = new PeopleResponsePojo();
                    responsePeople.setCode(EnumUtil.RESPONSE_CODE.UNKNOWN_ERROR);

                    if(callback != null){
                        callback.onListPeopleResponse(responsePeople);
                    }
                }
            });
        } catch (Exception error) {
            PeopleResponsePojo responsePeople = new PeopleResponsePojo();
            responsePeople.setCode(EnumUtil.RESPONSE_CODE.UNKNOWN_ERROR);

            if(error instanceof UnknownHostException){
                responsePeople.setCode(EnumUtil.RESPONSE_CODE.CONNECTION_ERROR);
            }

            if(callback != null){
                callback.onListPeopleResponse(responsePeople);
            }
        }
    }
}
