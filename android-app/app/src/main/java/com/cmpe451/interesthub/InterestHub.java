package com.cmpe451.interesthub;

import android.app.Application;
import android.util.Log;

import com.cmpe451.interesthub.controllers.SessionController;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.services.ApiService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eren on 27.10.2017.
 */

public class InterestHub extends Application {

    private SessionController sessionController;
    private ApiService apiService;

    public Content getTempContent() {
        return tempContent;
    }

    public void setTempContent(Content tempContent) {
        this.tempContent = tempContent;
    }

    private Content tempContent;

    public SessionController getSessionController() {
        if(sessionController == null){
            sessionController = new SessionController();
            Log.d("INFO","NEW SESSION CREATED");
        }


        return sessionController;
    }


    public ApiService getApiService() {
        if(apiService == null){

            Log.d("INFO","CREATING API SERVICE");

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://34.209.230.231:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
    public  void authApiService(final String token){

        Log.d("API","auth");
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://34.209.230.231:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(ApiService.class);
    }


}
