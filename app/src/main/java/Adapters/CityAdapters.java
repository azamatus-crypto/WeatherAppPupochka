package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.base.weatherapppupochka.R;

import java.util.ArrayList;

import Data.City;

public class CityAdapters extends RecyclerView.Adapter<CityAdapters.CityViewHolder> {
    private ArrayList<City> cities = new ArrayList<>();
    private OnCityClickListener onCityClickListener;

    public CityAdapters(OnCityClickListener onCityClickListener) {
        this.onCityClickListener = onCityClickListener;
    }

    public CityAdapters() {
    }

    public interface OnCityClickListener {
        void onCityClick(City city);
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.iteam_city, viewGroup, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int i) {
        holder.textViewSelectableCity.setText(cities.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewSelectableCity;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSelectableCity = itemView.findViewById(R.id.textViewSeleccitys);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCityClickListener != null) {
                        City city = cities.get(getAdapterPosition());
                        onCityClickListener.onCityClick(city);
                    }
                }
            });
        }
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public void addCity(City city) {
        this.cities.add(city);
        notifyDataSetChanged();
    }

    public void clear() {
        cities.clear();
        notifyDataSetChanged();
    }

    public void setOnCityClickListener(OnCityClickListener onCityClickListener) {
        this.onCityClickListener = onCityClickListener;
    }
}
