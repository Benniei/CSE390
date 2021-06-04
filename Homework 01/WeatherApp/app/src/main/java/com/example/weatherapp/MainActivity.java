/*
 * CSE 390: Mobile App Development
 * Name: Bennie Chen
 * Student ID: 112737201
 * Database Source: https://api.openweathermap.org/data/2.5/weather
 */
package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private String unit;
    private String location = "Stony Brook";

    /**
     * This method is called when the app is first created
     * @param savedInstanceState the previous instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unit = "imperial";
        weatherSetup("Unable to Fetch Data");
        initSwitch();
        initWeatherButton();
    }

    /**
     * Builds the URL which will be used to query the database
     * @param location location to query
     * @param units units that the data will be queried in
     * @return String value representing the link
     */
    private String buildURL(String location, String units){
        String apiKey = "appid=0f0a6ce49421f81eeff23827d50d8954";
        String host = "https://api.openweathermap.org/data/2.5/weather";
        location = "q=" + location.replaceAll(" ", "+");
        units = "units=" + units;
        return host + "?" + location + "&" + units + "&" + apiKey;
    }

    /**
     * This method converts Unix Time to current Time
     * Source: https://stackoverflow.com/questions/17432735/convert-unix-timestamp-to-date-in-java
     * @param unixTime String Value representing the Unix Time
     * @return String Value with the readable Time format
     */
    private String convertTime(String unixTime){
        Long timeLong = Long.parseLong(unixTime);
        Date date = new Date(timeLong*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        return sdf.format(date);
    }

    /**
     * This method is used to change a stream into a scanner
     * @param in input stream
     * @return String that is taken from the input stream
     * @throws IOException Input exception that may come from reading the stream
     */
    private String changeStreamtoString(InputStream in) throws IOException {
        /*
         * Source: https://stackoverflow.com/questions/309424/how-do-i-read-convert-an-inputstream-into-a-string-in-java
         * The reason it works is because Scanner iterates over tokens in the stream, and in this case we
         * separate tokens using "beginning of the input boundary" (\A), thus giving us only one token
         * for the entire contents of the stream.
         */
        Scanner s = new Scanner(in).useDelimiter("\\A");
        String results = "";
        if(s.hasNext())
            results = s.next();
        s.close();
        return results;
    }
    /**
     * Changes the unit the app will show
     */
    private void changeUnit(){
        if(unit.equals("imperial"))
            unit = "metric";
        else
            unit = "imperial";
    }

    /**
     * Initiates the Switch which will switch between C and F
     */
    private void initSwitch(){
        Switch unitSwitch = findViewById(R.id.temp_switch);

        unitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                changeUnit();
                weatherSetup("Unable to Fetch Data");
                Toast change;
                if(unit.equalsIgnoreCase("imperial")) {
                    change = Toast.makeText(getApplicationContext(), "Unit Changed to °F", Toast.LENGTH_SHORT);
                    change.getView().setBackgroundColor(getResources().getColor(R.color.toast_blue));
                }
                else {
                    change = Toast.makeText(getApplicationContext(), "Unit Changed to °C", Toast.LENGTH_SHORT);
                    change.getView().setBackgroundColor(getResources().getColor(R.color.toast_red));
                }
                change.show();
            }
        });
    }

    /**
     * Queries for the weather button
     */
    private void initWeatherButton(){
        Button weatherButton = findViewById(R.id.seeWeather);
        EditText cityText = findViewById(R.id.City_input);

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(cityText.getWindowToken(), 0);
                location = cityText.getText().toString();
                weatherSetup("City Not Found");
            }
        });
    }



    /**
     * This method sets up screen
     * @param error Error Message printed in toast
     */
    private void weatherSetup(String error){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    java.net.URL urlWeather = new URL(buildURL(location, unit));
                    HttpURLConnection urlConnection = (HttpURLConnection) urlWeather.openConnection();
                    urlConnection.connect();

                    if(urlConnection.getResponseCode() != 200) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView invalidCity = findViewById(R.id.Invalid);
                                invalidCity.setText(error);
                            }
                        });
                        return;
                    }
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String result = changeStreamtoString(in);
                    JSONObject json = new JSONObject(result);
                    urlConnection.disconnect();

                    // Extract details and main objects
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    JSONObject sys = json.getJSONObject("sys");

                    // Extract Fields
                    String temperature;
                    if(unit.equals("imperial"))
                        temperature = main.getString("temp") + "°F";
                    else
                        temperature = main.getString("temp") + "°C";
                    String location = json.getString("name");
                    String weatherCondition = details.getString("main");
                    String sunriseUnix = sys.getString("sunrise");
                    String sunsetUnix = sys.getString("sunset");
                    String icon = details.getString("icon");
                    String sunrise = "Sunrise: " + convertTime(sunriseUnix);
                    String sunset = "Sunset: " + convertTime(sunsetUnix);
                    TextView invalidCity = findViewById(R.id.Invalid);

                    URL imageURL = new URL("https://openweathermap.org/img/wn/" + icon + "@2x.png");
                    InputStream content = (InputStream)imageURL.getContent();
                    Drawable d = Drawable.createFromStream(content, "src");

                    // UI Components
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView locationText = findViewById(R.id.Location);
                            TextView currTempText = findViewById(R.id.Current_temp);
                            TextView weatherConditionsText = findViewById(R.id.WeatherConditions);
                            TextView sunriseText = findViewById(R.id.Sunrise);
                            TextView sunsetText = findViewById(R.id.Sunset);
                            ImageView weatherView = findViewById(R.id.Weather_img);

                            locationText.setText(location);
                            currTempText.setText(temperature);
                            weatherConditionsText.setText(weatherCondition);
                            sunriseText.setText(sunrise);
                            sunsetText.setText(sunset);
                            weatherView.setImageDrawable(d);
                            invalidCity.setText("");
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                    TextView invalidCity = findViewById(R.id.Invalid);
                }
            }
        });
        thread.start();
    }

}