package com.example.weatherviewer;

import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WeatherFragment extends Fragment {

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        WeatherDetails details = getArguments().getParcelable(MainActivity.WEATHER_KEY);

        TextView cityTextView = (TextView) view.findViewById(R.id.cityTextView);
        TextView currentTemp = (TextView) view.findViewById(R.id.currentTemperature);
        TextView highTemp = (TextView) view.findViewById(R.id.highTemperature);
        TextView lowTemp = (TextView) view.findViewById(R.id.lowTemperature);
        TextView description = (TextView) view.findViewById(R.id.weatherDescription);
        TextView currentTemp1 = (TextView) view.findViewById(R.id.currentTemperature);
        TextView highTemp1 = (TextView) view.findViewById(R.id.highTemperature);
        TextView lowTemp1 = (TextView) view.findViewById(R.id.lowTemperature);
        TextView description1 = (TextView) view.findViewById(R.id.weatherDescription);

        if( details != null) {
            cityTextView.setText(details.getCity());
            currentTemp.setText(
                    String.format(
                            getResources().getString(R.string.temperature_celsius),
                            details.getCurrentTemperature()
                    )
            );
            highTemp.setText(
                    String.format(
                            getResources().getString(R.string.temperature_celsius),
                            details.getHighTemperature()
                    ));
            lowTemp.setText(
                    String.format(
                            getResources().getString(R.string.temperature_celsius),
                            details.getLowTemperature()
                    ));
            description.setText(details.getDescription());
        } else {
            Log.d(MainActivity.LOG_KEY, "WeatherDetails is null");
        }
        if( details != null) {
            cityTextView.setText(details.getCity());
            currentTemp1.setText(
                    String.format(
                            getResources().getString(R.string.temperature_celsius),
                            details.getCurrentTemperature1()
                    )
            );
            highTemp1.setText(
                    String.format(
                            getResources().getString(R.string.temperature_celsius),
                            details.getHighTemperature1()
                    ));
            lowTemp1.setText(
                    String.format(
                            getResources().getString(R.string.temperature_celsius),
                            details.getLowTemperature1()
                    ));
            description1.setText(details.getDescription());
        } else {
            Log.d(MainActivity.LOG_KEY, "WeatherDetails is null");
        }

        return view;
    }
}
