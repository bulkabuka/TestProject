package org.leftbrained.testproject;

import android.os.Bundle;
import android.util.Pair;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TestInterface testInterface = retrofit.create(TestInterface.class);
        RadioGroup group = findViewById(R.id.radioGroup);
        group.setOnCheckedChangeListener(((group1, checkedId) -> {
                    if (checkedId == R.id.populationTop) {
                        populationTop(testInterface);
                    } else if (checkedId == R.id.randomlyBtn) {
                        randomlyChart(testInterface);
                    }
                })
        );
    }

    public void populationTop(TestInterface testInterface) {
        Call<List<Country>> call = testInterface.getAllCountries();
        call.enqueue(
                new Callback<List<Country>>() {
                    @Override
                    public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                        assert response.body() != null;
                        List<Country> countries = response.body();

                        List<Pair<String, Integer>> combinedList = new ArrayList<>();
                        for (int i = 0; i < countries.size(); i++) {
                            Country country = countries.get(i);
                            int population = Integer.parseInt(country.getPopulationCountry());
                            combinedList.add(new Pair<>(country.getNameCountry(), population));
                        }

                        combinedList.sort((pair1, pair2) -> pair2.second.compareTo(pair1.second));
                        ArrayList<String> labels = new ArrayList<>();
                        ArrayList<BarEntry> entries = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            Pair<String, Integer> pair = combinedList.get(i);
                            entries.add(new BarEntry(pair.second, i));
                            labels.add(pair.first);
                        }

                        BarDataSet set = new BarDataSet(entries, "Страны");
                        BarData data = new BarData(labels, set);
                        BarChart chart = findViewById(R.id.chart);
                        chart.setData(data);
                        chart.enableScroll();
                        chart.isPinchZoomEnabled();
                        chart.invalidate();
                    }

                    @Override
                    public void onFailure(Call<List<Country>> call, Throwable t) {
                        t.printStackTrace();
                    }
                }
        );
    }

    public void randomlyChart(TestInterface testInterface) {
        Call<List<Country>> call = testInterface.getAllCountries();
        call.enqueue(
                new Callback<List<Country>>() {
                    @Override
                    public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                        assert response.body() != null;
                        List<Country> countries = response.body();
                        Set<Integer> selectedIndices = new HashSet<>();
                        ArrayList<String> labels = new ArrayList<>();
                        ArrayList<BarEntry> entries = new ArrayList<>();

                        for (int i = 0; i < 5; i++) {
                            int randomIndex;
                            do {
                                randomIndex = (int) (Math.random() * countries.size());
                            } while (selectedIndices.contains(randomIndex));

                            selectedIndices.add(randomIndex);
                            Country country = countries.get(randomIndex);
                            int population = Integer.parseInt(country.getPopulationCountry());
                            entries.add(new BarEntry((float) population, i));
                            labels.add(country.getNameCountry());
                        }

                        BarDataSet set = new BarDataSet(entries, "Страны");
                        BarData data = new BarData(labels, set);
                        BarChart chart = findViewById(R.id.chart);
                        chart.setData(data);
                        chart.enableScroll();
                        chart.isPinchZoomEnabled();
                        chart.invalidate();
                    }

                    @Override
                    public void onFailure(Call<List<Country>> call, Throwable t) {
                        t.printStackTrace();
                    }
                }
        );
    }
}