package com.teamwork.example.service;

import android.support.annotation.NonNull;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamwork.example.pojo.response.ExampleResponsePojo;
import com.teamwork.example.util.ConstUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ExampleServiceR {

    private static final String BASE_URL = "https://yat.teamwork.com/";
    private static Retrofit retrofit;

    protected abstract void validateRequest() throws Exception;

    protected static Retrofit getRetrofit(){
        if(retrofit == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.interceptors().add(getMaxploreInterceptor());
            builder.readTimeout(1, TimeUnit.MINUTES);
            builder.connectTimeout(1, TimeUnit.MINUTES);

            OkHttpClient client = builder.build();

            Gson gson = new GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }

        return retrofit;
    }

    protected <T extends ExampleResponsePojo> T setPagination(@NonNull Response<? extends ExampleResponsePojo> response){
        T result;

        //Not sure about this. It's safe but might have a better way.
        try{
            result = (T)response.body();

            int page = Integer.parseInt(response.headers().get("X-Page"));
            int totalPages = Integer.parseInt(response.headers().get("X-Pages"));

            result.setHasMore(totalPages > page);
            result.setNextPage(page++);

        }catch (Exception error){
            result = (T) new ExampleResponsePojo();
        }

        return result;
    }

    private static Interceptor getMaxploreInterceptor(){
        return chain -> {
            String authentication = Base64.encodeToString((ConstUtil.Config.TEAMWORK_API_KEY + ":" + "X").getBytes("UTF-8"), Base64.NO_WRAP|Base64.URL_SAFE);
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Basic " + authentication)
                    .addHeader("User-Agent", "Example-App")// Not necessary but good to have
                    .addHeader("X-Requested-With", "Example-App")// Not necessary but good to have
                    .method(chain.request().method(), chain.request().body())
                    .build();

            return chain.proceed(newRequest);
        };
    }
}
