package Adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.base.weatherapppupochka.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Data.Weatherentr;
import Utils.SimpJson;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private Context context;
    private ArrayList<Weatherentr> weatherEntries = new ArrayList<>();

    public WeatherAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.iteam_weather, viewGroup, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {
        Weatherentr weatherEn = weatherEntries.get(i);
        String temp = (int) weatherEn.getTemp() + "°";
        weatherViewHolder.textViewTemperature.setText(temp);
        String date = SimpJson.getDateAndTime(weatherEn.getDate());
        weatherViewHolder.textViewDate.setText(date);
       Glide.with(context).load(Uri.parse(weatherEn.getIcon())).into(weatherViewHolder.imageViewIcon);
               //Uri.parse(weatherEn.getIcon()), weatherViewHolder.imageViewIcon);
    }

    @Override
    public int getItemCount() {
        return weatherEntries.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewIcon;
        private TextView textViewDate;
        private TextView textViewTemperature;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
            textViewDate = itemView.findViewById(R.id.textViewDateopachki);
            textViewTemperature = itemView.findViewById(R.id.textViewTemperature);
        }
    }

    public void setWeatherEntries(ArrayList<Weatherentr> weatherEntries) {
        this.weatherEntries = weatherEntries;
        notifyDataSetChanged();
    }

    public void addWeatherEntries(Weatherentr weatherEntry) {
        this.weatherEntries.add(weatherEntry);
        notifyDataSetChanged();
    }

    public void clear() {
        this.weatherEntries.clear();
        notifyDataSetChanged();
    }
}
