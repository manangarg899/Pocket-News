package com.example.manan.pocketnews;

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

/**
 * Created by Manan on 02-03-2017.
 */

public class NewsQuery {

    public static List<News> fetchNewsData(String request_Url) {
        URL url = createUrl(request_Url);

        String jSonResponse = null;

        try {
            jSonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("Log message", "Problem making the HTTP request.", e);
        }
        List<News> news = extractFeaturesFromJson(jSonResponse);

        return news;
    }

    private static URL createUrl(String inputUrl) {
        URL url = null;
        try {
            url = new URL(inputUrl);
        } catch (MalformedURLException e) {
            Log.e("Log message", "Problem building the URL ", e);
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;

        if (url == null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;

        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("log message", "Error Response Code" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();

            if (inputStream != null)
                inputStream.close();
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


    private static List<News> extractFeaturesFromJson(String NewsJson) {
        if (TextUtils.isEmpty(NewsJson))
            return null;

        List<News> news = new ArrayList<News>();

        try {
            JSONObject baseJsonResponse = new JSONObject(NewsJson);

            JSONObject response = baseJsonResponse.getJSONObject("response");


            JSONArray resultArray = response.getJSONArray("results");

            for (int i = 0; i < resultArray.length(); i++) {

                JSONObject currentNews = resultArray.getJSONObject(i);

                JSONObject fields = currentNews.getJSONObject("fields");

                String headline = "No HeadLine Found";

                String byLine = "No Writer Found";

                String standFirst = "No Description Found";

                String shortUrl = null;

                String thumbnailUrl = "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQOF6YZ8M0KhCq_HFv7yjXEXWlQfYPD3v80B8Nxs_lbHApPh0Ps";

                if (fields.has("headline")) {

                    headline = fields.getString("headline");
                }

                if (fields.has("byline")) {

                    byLine = fields.getString("byline");
                }

                if (fields.has("standfirst")) {
                    standFirst = fields.getString("standfirst");
                }

                if (fields.has("shortUrl")) {
                    shortUrl = fields.getString("shortUrl");
                }

                if (fields.has("thumbnail")) {

                    thumbnailUrl = fields.getString("thumbnail");
                }

                News news1 = new News(headline, standFirst, byLine, shortUrl, thumbnailUrl);
                news.add(news1);

            }
        } catch (JSONException e1) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results");
        }

        return news;
    }

}
