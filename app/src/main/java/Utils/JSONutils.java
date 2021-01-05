package Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Data.Weatherentr;

public class JSONutils {
    private static final String KEY_DATE = "now";
    private static final String KEY_FACT = "fact";
    private static final String KEY_TEMPERATURE = "temp";
    private static final String KEY_ICON = "icon";
    private static final String KEY_CONDITION = "condition";
    private static final String KEY_WIND_SPEED = "wind_speed";
    private static final String KEY_WIND_DIRECTORY = "wind_dir";
    private static final String KEY_PRESSURE_MM = "pressure_mm";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_INFO = "info";
    private static final String KEY_TZ_INFO = "tzinfo";
    private static final String KEY_CITY_NAME = "name";

    private static final String KEY_FORECAST = "forecasts";
    private static final String KEY_HOURS = "hours";
    private static final String KEY_DATE_FORECAST = "hour_ts";

    private static ArrayList<Weatherentr> getForecast(JSONObject mainJSONObject, int from, int until) {
        ArrayList<Weatherentr> result = new ArrayList<>();
        try {
            JSONArray jsonArray = mainJSONObject.getJSONArray(KEY_FORECAST);
            for (int i = from; i < until; i++) {
                JSONObject jsonObjectForecast = jsonArray.getJSONObject(i);
                JSONArray jsonArrayHoursForecast = jsonObjectForecast.getJSONArray(KEY_HOURS);
                for (int j = 0; j < jsonArrayHoursForecast.length(); j++) {
                    if (j % 3 == 0) {
                        JSONObject jsonObject = jsonArrayHoursForecast.getJSONObject(j);
                        Weatherentr weatherentr = getWeatherFromForecast(jsonObject);
                        if (weatherentr != null) {
                            result.add(weatherentr);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Weatherentr> getForecastToday(JSONObject mainJSONObject) {
        return getForecast(mainJSONObject, 0, 1);
    }

    public static ArrayList<Weatherentr> getForecastTomorrow(JSONObject mainJSONObject) {
        return getForecast(mainJSONObject, 1, 2);
    }

    public static ArrayList<Weatherentr> getForecastForThreeDays(JSONObject mainJSONObject) {
        return getForecast(mainJSONObject, 0, 3);
    }

    public static Weatherentr getWeatherEntryForTomorrow(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_FORECAST);
            if (jsonArray != null && jsonArray.length() > 0) {
                JSONObject jsonObjectForecast = jsonArray.getJSONObject(1);
                JSONArray jsonArrayHoursForecast = jsonObjectForecast.getJSONArray(KEY_HOURS);
                if (jsonArrayHoursForecast != null && jsonArrayHoursForecast.length() > 12) {
                    JSONObject jsonObjectTomorrowWeather = jsonArrayHoursForecast.getJSONObject(12);
                    return getWeatherFromForecast(jsonObjectTomorrowWeather);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Weatherentr getWeatherEntryFromJSON(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        try {
            long date = jsonObject.getLong(KEY_DATE);
            JSONObject jsonObjectInfo = jsonObject.getJSONObject(KEY_INFO);
            JSONObject jsonObjectTZInfo = jsonObjectInfo.getJSONObject(KEY_TZ_INFO);
            String city = jsonObjectTZInfo.getString(KEY_CITY_NAME);
            JSONObject jsonObjectFact = jsonObject.getJSONObject(KEY_FACT);
            double temp = jsonObjectFact.getDouble(KEY_TEMPERATURE);
            String icon = jsonObjectFact.getString(KEY_ICON);
            String fullIconPath = NetworkUtils.BASE_IMAGE_URL + icon + NetworkUtils.BASE_IMAGE_URL_END;
            String condition = jsonObjectFact.getString(KEY_CONDITION);
            double windSpeed = jsonObjectFact.getDouble(KEY_WIND_SPEED);
            String windDirectory = jsonObjectFact.getString(KEY_WIND_DIRECTORY);
            double pressure = jsonObjectFact.getDouble(KEY_PRESSURE_MM);
            double humidity = jsonObjectFact.getDouble(KEY_HUMIDITY);
            return new Weatherentr(date, temp, fullIconPath, condition, windSpeed, windDirectory, pressure, humidity, city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Weatherentr getWeatherFromForecast(JSONObject jsonObject) {
        try {
            long date = jsonObject.getLong(KEY_DATE_FORECAST);
            if (date < System.currentTimeMillis() / 1000) {
                return null;
            }
            String city = "";
            double temp = jsonObject.getDouble(KEY_TEMPERATURE);
            String icon = jsonObject.getString(KEY_ICON);
            String fullIconPath = NetworkUtils.BASE_IMAGE_URL + icon + NetworkUtils.BASE_IMAGE_URL_END;
            String condition = jsonObject.getString(KEY_CONDITION);
            double windSpeed = jsonObject.getDouble(KEY_WIND_SPEED);
            String windDirectory = jsonObject.getString(KEY_WIND_DIRECTORY);
            double pressure = jsonObject.getDouble(KEY_PRESSURE_MM);
            double humidity = jsonObject.getDouble(KEY_HUMIDITY);
            return new Weatherentr(date, temp, fullIconPath, condition, windSpeed, windDirectory, pressure, humidity, city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
