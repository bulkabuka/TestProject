package org.leftbrained.testproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private List<Country> countryList;

    public CountryAdapter(List<Country> countryList) {
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = countryList.get(position);
        holder.textViewCountryName.setText(country.getNameCountry());
        holder.countryPopulation.setText(String.valueOf(country.getPopulationCountry()));
        Picasso.with(holder.countryFlag.getContext()).load(country.getFlag()).into(holder.countryFlag);
    }

    public void updateCountries(List<Country> countries) {
        countryList.clear();
        countryList.addAll(countries);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCountryName;
        TextView countryPopulation;
        ImageView countryFlag;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCountryName = itemView.findViewById(R.id.textViewCountryName);
            countryPopulation = itemView.findViewById(R.id.countryPopulation);
            countryFlag = itemView.findViewById(R.id.countryFlag);
        }
    }
}
