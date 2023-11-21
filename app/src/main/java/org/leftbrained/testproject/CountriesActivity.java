package org.leftbrained.testproject;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Context ctx = this;
        TestInterface testInterface = retrofit.create(TestInterface.class);
        getCountries(testInterface, ctx);
    }

    public void getCountries(TestInterface testInterface, Context ctx) {
        Call<List<Country>> call = testInterface.getAllCountries();
        call.enqueue(
                new Callback<List<Country>>() {

                    @Override
                    public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                        RecyclerView recyclerViewCountries = findViewById(R.id.countries_recycler_view);
                        recyclerViewCountries.setLayoutManager(new LinearLayoutManager(ctx));
                        CountryAdapter adapter = new CountryAdapter(new ArrayList<>());
                        recyclerViewCountries.setAdapter(adapter);
                        adapter.updateCountries(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Country>> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                }
        );
    }

}