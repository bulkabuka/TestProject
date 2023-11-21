package org.leftbrained.testproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestInterface {
    @GET("all")
    Call<List<Country>> getAllCountries();
}

