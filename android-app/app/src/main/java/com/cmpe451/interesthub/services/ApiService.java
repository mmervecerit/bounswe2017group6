package com.cmpe451.interesthub.services;

import com.cmpe451.interesthub.models.Dummy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by eren on 22.10.2017.
 */

public interface ApiService {

    @GET("dummy/")
    Call<List<Dummy>> getDummy();
}
