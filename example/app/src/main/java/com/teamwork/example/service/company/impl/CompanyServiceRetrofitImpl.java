package com.teamwork.example.service.company.impl;

import android.support.annotation.NonNull;

import com.teamwork.example.pojo.response.CompaniesResponsePojo;
import com.teamwork.example.service.ExampleServiceR;
import com.teamwork.example.service.company.callback.CompanyServiceCallback;
import com.teamwork.example.service.company.contract.CompanyService;
import com.teamwork.example.service.company.endpoint.CompanyEndPointR;
import com.teamwork.example.util.EnumUtil;
import com.teamwork.example.util.FunctionUtil;

import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyServiceRetrofitImpl extends ExampleServiceR implements CompanyService{
    private CompanyServiceCallback callback;

    public CompanyServiceRetrofitImpl(CompanyServiceCallback callback){
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
                callback.onCompanyRequestError(EnumUtil.RESPONSE_CODE.CONNECTION_ERROR);

            throw new UnknownHostException();
        }
    }

    //There's too much repeated code here. I need to have a look at how to improve this process
    @Override
    public void listCompanies(int pageCursor) {
        try {
            validateRequest();

            Call<CompaniesResponsePojo> call  = getRetrofit().create(CompanyEndPointR.class).listCompanies(pageCursor);

            call.enqueue(new Callback<CompaniesResponsePojo>() {
                @Override
                public void onResponse(@NonNull Call<CompaniesResponsePojo> call, @NonNull Response<CompaniesResponsePojo> response) {
                    CompaniesResponsePojo responseCompanies;

                    //It's not safe to assume that a success http request is equivalent to success on the host
                    if(response.body() != null && response.code() == 200){
                        responseCompanies = setPagination(response);
                        responseCompanies.setCode(EnumUtil.RESPONSE_CODE.SUCCESS);

                    }else {
                        responseCompanies = new CompaniesResponsePojo();
                        responseCompanies.setCode(EnumUtil.RESPONSE_CODE.fromIntCode(response.code()));
                    }

                    if(callback != null){
                        callback.onListCompaniesResponse(responseCompanies);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CompaniesResponsePojo> call, @NonNull Throwable throwable) {
                    CompaniesResponsePojo responseCompanies = new CompaniesResponsePojo();
                    responseCompanies.setCode(EnumUtil.RESPONSE_CODE.UNKNOWN_ERROR);

                    if(callback != null){
                        callback.onListCompaniesResponse(responseCompanies);
                    }
                }
            });
        } catch (Exception error) {
            CompaniesResponsePojo responseCompanies = new CompaniesResponsePojo();
            responseCompanies.setCode(EnumUtil.RESPONSE_CODE.UNKNOWN_ERROR);

            if(error instanceof UnknownHostException){
                responseCompanies.setCode(EnumUtil.RESPONSE_CODE.CONNECTION_ERROR);
            }

            if(callback != null){
                callback.onListCompaniesResponse(responseCompanies);
            }
        }
    }
}
