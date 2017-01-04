package com.whimsygames.whatstheweatherdemo;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherDownloader extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... urls) {

        String content = "";

        URL url;
        try {
            url = new URL(urls[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.connect();

            InputStreamReader inputStream = new InputStreamReader(url.openStream());
            BufferedReader reader = new BufferedReader(inputStream);

            StringBuilder stringBuilder = new StringBuilder();

            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            inputStream.close();
            reader.close();

            content = stringBuilder.toString().trim();

        } catch (MalformedURLException e) {
            Log.e("ERROR", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        return content;
    }

    @Override
    protected void onPostExecute(String content) {
        super.onPostExecute(content);

        StringBuilder sb = new StringBuilder();

        try {
            JSONObject jsonObject = new JSONObject(content);

            String weather = jsonObject.getString("weather");

            Log.i("Weather content", weather);

            JSONArray jsonArray = new JSONArray(weather);


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPart = jsonArray.getJSONObject(i);
                sb.append(jsonPart.getString("main")).append('\n');
                sb.append(jsonPart.getString("description")).append('\n');
            }
        } catch (JSONException e) {
            Log.e("ERROR", e.getMessage(), e);
        }

        delegate.processFinish(sb.toString());
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public WeatherDownloader(AsyncResponse delegate) {
        this.delegate = delegate;
    }

}
