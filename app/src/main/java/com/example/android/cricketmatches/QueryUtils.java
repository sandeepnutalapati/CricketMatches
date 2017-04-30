package com.example.android.cricketmatches;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {


    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    private QueryUtils() {
    }

    public static CricketScoreData fetchCricketScoreData(String requestUrl) {

        //just to add delay such that we can see progress bar
     /*   try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        CricketScoreData cricketScoreData = extractScoreFeatureFromJson(jsonResponse);

        return cricketScoreData;
    }

    public static List<CricketMatchData> fetchCricketMatchData(String requestUrl) {

        //just to add delay such that we can see progress bar
     /*   try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        List<CricketMatchData> cricketMatchDatas = extractMatchFeatureFromJson(jsonResponse);

        return cricketMatchDatas;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    private static List<CricketMatchData> extractMatchFeatureFromJson(String cricketJSON) {
       if (TextUtils.isEmpty(cricketJSON)) {
            return null;
        }


        List<CricketMatchData> cricketMatchDatas = new ArrayList<>();

        try {


            JSONObject baseJsonResponse = new JSONObject(cricketJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray cricketArray = baseJsonResponse.getJSONArray("data");


            for (int i = 0; i < cricketArray.length(); i++) {


                JSONObject currentMatch = cricketArray.getJSONObject(i);

                String name=currentMatch.getString("name");
                String date = currentMatch.getString("date");
                String unique_id=currentMatch.getString("unique_id");

                CricketMatchData matchData = new CricketMatchData(name,unique_id,date);

                cricketMatchDatas.add(matchData);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the cricket JSON results", e);
        }


        return cricketMatchDatas;
    }

    private static CricketScoreData extractScoreFeatureFromJson(String cricketJSON) {
        if (TextUtils.isEmpty(cricketJSON)) {
            return null;
        }

        String datetimeGmt;
        String type;
        String comment;
        String score;


        CricketScoreData scoreData = null;
        try {


            JSONObject baseJsonResponse = new JSONObject(cricketJSON);


            datetimeGmt = baseJsonResponse.getString("dateTimeGMT");
            type = baseJsonResponse.getString("type");
            comment = baseJsonResponse.getString("innings-requirement");
            score = baseJsonResponse.getString("score");
            scoreData = new CricketScoreData(datetimeGmt, type, comment, score);
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the cricket JSON results", e);
        }


        return scoreData;
    }
}