package com.whimsygames.whatstheweatherdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // http://openweathermap.org/api

    }

    protected void check(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);


        String zip = editText.getText().toString();

        String url = String.format("http://api.openweathermap.org/data/2.5/weather?zip=%s&APPID=27fa8d9549084afa85770308c06904c2", zip);
        String icons = "http://openweathermap.org/img/w/04n.png";

        WeatherDownloader task = new WeatherDownloader(asyncResponse);
        task.execute(url);
    }


    WeatherDownloader.AsyncResponse asyncResponse = new WeatherDownloader.AsyncResponse() {

        @Override
        public void processFinish(String result) {
            Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
            toast.show();
        }
    };
}
