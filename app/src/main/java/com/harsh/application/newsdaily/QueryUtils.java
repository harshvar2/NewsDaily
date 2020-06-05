package com.harsh.application.newsdaily;

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
    private static String LOG_TAG = "Test-Case-Error-generated";
    private static String LOG_TAG_output = "Test-Case-Output-generated";
    /**
     * Sample JSON response for a USGS query
     */

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link NewsData} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<NewsData> extractFeatureFromJson(String newsJSON) {




        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<NewsData> arrayList = new ArrayList<NewsData>();


        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);

            JSONArray newsArray = baseJsonResponse.getJSONArray("articles");

            for (int i = 0; i < newsArray.length(); i++) {

                JSONObject articles = newsArray.getJSONObject(i);


                String title = articles.getString("title");

                String Description = articles.getString("description");

                String webUrl = articles.getString("url");



                String sectionName = articles.getString("content");


                String MergeDate = articles.optString("publishedAt");
                String[] dateTime = MergeDate.split("T");
                String date = dateTime[0];
                String time = dateTime[1];
                time = time.substring(0, 5);



                String author = articles.getString("author");

                Log.i(LOG_TAG_output, time);
                Log.i(LOG_TAG_output, date);
                Log.i(LOG_TAG_output, title);
                Log.i(LOG_TAG_output, webUrl);
                Log.i(LOG_TAG_output, author);
                Log.i(LOG_TAG_output, sectionName);
                NewsData newsData = new NewsData(title, webUrl, sectionName, date, time, author);
                arrayList.add(newsData);


            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the newsAPI JSON results", e);
        }

        // Return the list of newsData
        return arrayList;
    }

    public static List<NewsData> fetchEarthquakeData(String requestUrl) {
        //Create url object
        URL url = createUrl(requestUrl);

        //Perform Httprequest and reciever the Json repsonsse

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException r) {
            Log.e(LOG_TAG, "Problem making a HTTP request", r);
        }

        //Extract relavant fields from the JSON and create a list

        List<NewsData> earthquakes = extractFeatureFromJson(jsonResponse);

        //REturn the list of earthquakes

        return earthquakes;
    }

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

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
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

}