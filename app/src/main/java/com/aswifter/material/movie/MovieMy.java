package com.aswifter.material.movie;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.util.List;

/**
 * Created by Administrator on 2015/11/10.
 */
public class MovieMy {

    private static String Url = "http://api.douban.com/v2/movie/us_box";
//    private static String Url = "http://192.168.0.102:8080/doubanJson.txt";


    private static List<Us_Box.Subjects> subjectsList = null;


    public static List<Us_Box.Subjects> InitUs_Box() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL cURL = new URL(Url);

                    try {
                        HttpURLConnection connection = (HttpURLConnection) cURL.openConnection();

                        connection.setRequestMethod("GET");
                        connection.setReadTimeout(8000);
                        connection.setReadTimeout(8000);

                        InputStream in = connection.getInputStream();

                        BufferedReader bf = new BufferedReader(new InputStreamReader(in));

                        StringBuilder stringBuilder = new StringBuilder();

                        String line = "";

                        while ((line = bf.readLine()) != null) {
                            stringBuilder.append(line);
                        }

                        try {
                            JSONObject jsonObject = new JSONObject(stringBuilder.toString());


                            JSONArray subArray = jsonObject.getJSONArray("subjects");


                            Gson gson = new Gson();

                            Us_Box allData=gson.fromJson(jsonObject.toString(),Us_Box.class);


                                    subjectsList = gson.fromJson(subArray.toString(),
                                    new TypeToken<List<Us_Box.Subjects>>() {
                                    }.getType());

                            if(subjectsList!=null)
                            {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        return subjectsList;
    }

}
