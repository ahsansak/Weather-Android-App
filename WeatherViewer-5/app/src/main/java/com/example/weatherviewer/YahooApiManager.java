package com.example.weatherviewer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class YahooApiManager {

    private String city;

    public YahooApiManager(String city) {
        this.city = city;
    }

    public WeatherDetails getWeather() throws ApiException {

        JSONObject json = makeRequest();
        WeatherDetails details = parseJson(json);
        return details;
    }

    private JSONObject makeRequest() throws ApiException {

        HttpURLConnection urlConnection;
        URL url;
        JSONObject json = null;

        String yql = String.format(
            "select * from weather.forecast where u=\"c\" and woeid in (select woeid from geo.places(1) where text=\"%s\")",
            city
        );

        try {

            yql = URLEncoder.encode(yql, "utf-8");

            StringBuilder urlString = new StringBuilder();
            urlString.append("https://query.yahooapis.com/v1/public/yql?");
            urlString.append("q=").append(yql);
            urlString.append("&format=").append("json");

            url = new URL(urlString.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(
                new InputStreamReader(inStream)
            );

            String temp;
            StringBuilder response = new StringBuilder();

            while(( temp = bReader.readLine()) != null) {
                response.append(temp);
            }

            json = (JSONObject) new JSONTokener(
                response.toString()
            ).nextValue();

        } catch(IOException|JSONException e) {
            Log.e(MainActivity.LOG_KEY, e.getClass().getName() + ": " + e.getMessage());
            throw new ApiException("Unable to process request");
        }

        return json;
    }

    private WeatherDetails parseJson(JSONObject json) throws ApiException {

        WeatherDetails details = null;

        if( json == null ) {
            Log.d(MainActivity.LOG_KEY, "parseJson: parameter is null");
            return null;
        }

        try {
            JSONObject item = json
                .getJSONObject("query")
                .getJSONObject("results")
                .getJSONObject("channel")
                .getJSONObject("item");

            JSONObject condition = item.getJSONObject("condition");

            int currentTemperature = condition.getInt("temp");
            String description = condition.getString("text");

            JSONArray forecast = item.getJSONArray("forecast");

            int highTemperature = forecast.getJSONObject(0).getInt("high");
            int lowTemperature = forecast.getJSONObject(0).getInt("low");

            details = new WeatherDetails(
                    currentTemperature,
                    highTemperature,
                    lowTemperature,
                    description,
                    currentTemperature,
                    highTemperature,
                    lowTemperature,
                    description,
                    city
            );

        } catch( JSONException e ) {
            Log.e(MainActivity.LOG_KEY, e.getClass().getName() + ": " + e.getMessage());
            throw new ApiException("Unable to process request");
        }

        return details;
    }
}
