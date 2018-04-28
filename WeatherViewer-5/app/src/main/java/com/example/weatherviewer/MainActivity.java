package com.example.weatherviewer;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnWeatherRequestCompleted {

    public static final String WEATHER_KEY = "weatherdetails";
    public static final String LOG_KEY = "WeatherViewer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(onSearchButtonClick);
    }

    public View.OnClickListener onSearchButtonClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            EditText searchText = (EditText) findViewById(R.id.searchText);
            String city = searchText.getText().toString();

            WeatherRequest task = new WeatherRequest(
                    MainActivity.this,
                    getApplicationContext()
            );
            task.execute(city);
        }
    };

    @Override
    public void OnTaskCompleted(WeatherDetails details) {

        Bundle args = new Bundle();
        args.putParcelable(WEATHER_KEY, details);

        WeatherFragment f = new WeatherFragment();
        f.setArguments(args);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.weatherFragment, f).commit();
    }

    public static class WeatherRequest extends AsyncTask<String, Void, WeatherDetails> {

        private OnWeatherRequestCompleted listener;
        private Context context;

        private String city;
        private ApiException e;

        public WeatherRequest(
                OnWeatherRequestCompleted listener,
                Context context
        ) {
            this.listener = listener;
            this.context = context;
        }

        @Override
        protected WeatherDetails doInBackground(String... params) {

            city = params[0];
            WeatherDetails details = null;

            try {
                YahooApiManager apiManager = new YahooApiManager(city);
                details = apiManager.getWeather();
            } catch (ApiException e ) {
                Log.e(MainActivity.LOG_KEY, e.getClass().getName() + ": " + e.getMessage());
                this.e = e;
            }

            return details;
        }

        @Override
        protected void onPostExecute(WeatherDetails weatherData) {
            super.onPostExecute(weatherData);

            if( this.e != null ) {

                CharSequence text = String.format(
                    context.getResources().getString(R.string.search_error),
                    city
                );
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
            }

            listener.OnTaskCompleted(weatherData);
        }
    }
}
