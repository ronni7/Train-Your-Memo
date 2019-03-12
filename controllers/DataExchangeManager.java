package controllers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class DataExchangeManager {
    public static JSONArray jsonArray;
    public static JSONObject jsonObject;

    public static BestScoreEntity getTopPlayerScoreAtLevel(String login, String validationKey, String level) throws NullPointerException, IOException {
        BestScoreEntity bestScoreEntity;
        HttpURLConnection con;
        URL url;
        url = new URL("http://localhost:8080/demo/bestScoreByLevel?login=" + login + "&validationKey=" + validationKey + "&level=" + level);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");

        try {
            jsonObject = new JSONObject(parseResponse(con));
            bestScoreEntity = new BestScoreEntity(jsonObject.getString("score"), jsonObject.getString("pack"));
        } catch (JSONException e) {
            bestScoreEntity = new BestScoreEntity("00:00:00", "");
        }

        //  System.out.println(bestScoreEntity);
        return bestScoreEntity;
    }

    private static String parseResponse(HttpURLConnection con) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);
        in.close();
        // System.out.println("response  " +response.toString());
        //  System.out.println(response.toString());
        return response.toString();
    }

    public static boolean insertNewScore(String login, Time score, String pack, String validationKey, LEVELS level) throws NullPointerException, IOException {
        HttpURLConnection con = null;
        URL url = null;
        url = new URL("http://localhost:8080/demo/add?login=" + login + "&score=" + score + "&pack=" + pack + "&validationKey=" + validationKey + "&level=" + level);
        con = (HttpURLConnection) url.openConnection();
        //System.out.println(url);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");
        return Boolean.valueOf(parseResponse(con));
    }

    public static ArrayList<ListViewEntity> loadHighscoreTable(String login, String validationKey, String selectedLevel) throws NullPointerException, IOException, JSONException {
        HttpURLConnection con = null;
        URL url = null;
        ArrayList<ListViewEntity> list = new ArrayList<>();
        //   try {
        url = new URL("http://localhost:8080/demo/all?login=" + login + "&level=" + selectedLevel + "&validationKey=" + validationKey);
        //   } catch (
        //           MalformedURLException e) {
        //   System.out.println("I said that 1");
        //       throw e;
        //   }
        //   try {
        con = (HttpURLConnection) url.openConnection();
        //   } catch (
        //          IOException e) {
        //   System.out.println("I said that 2");
        //     throw e;
        //  }
        con.setRequestProperty("Content-Type", "application/json");
        //  try {
        con.setRequestMethod("GET");
        //    } catch (ProtocolException e) {
        //      System.out.println("I said that 3");
        //     throw e;
        //  }

        try {


            jsonArray = new JSONArray(parseResponse(con));

            for (int i = 0; i < jsonArray.length(); i++) {
                //  System.out.println(jsonArray.getJSONObject(i).toString());
                jsonObject = jsonArray.getJSONObject(i);

// TODO: 2019-02-08 zastanów sie nad liczbami porzadkowymi... 
                ListViewEntity lve = new ListViewEntity(i + 1 + ". " +
                        jsonObject.getString("nickname"),
                        jsonObject.getString("score"),
                        jsonObject.getString("pack"),
                        jsonObject.getString("level")

                );
                //    System.out.println(lve);
                list.add(lve);
                /*System.out.println(jsonObject.getString("score"));
                System.out.println(jsonObject.get("user") instanceof JSONObject);
                System.out.println(jsonObject.get("user").toString());*/
            }
           /*  jsonObject = new JSONObject(jsonstring.substring(1,jsonstring.length()-1));
            System.out.println(jsonObject.toString());
            System.out.println(jsonObject.get("id"));
            System.out.println(jsonObject.getString("score"));
            System.out.println(jsonObject.get("user") instanceof JSONObject);
            System.out.println(jsonObject.get("user").toString());*/
        } catch (IOException e) {
            //  e.printStackTrace();
            con.disconnect();
            throw e;
        } catch (JSONException e) {
            //  e.printStackTrace();
            con.disconnect();
            throw e;


        }
        return list;
        //  return jsonArray;
        //return jsonObject;
    }

    public static boolean validateKey(String login, String validationKey) throws NullPointerException, IOException {
        HttpURLConnection con = null;
        URL url = null;
        url = new URL("http://localhost:8080/demo/validateKey?login=" + login + "&validKey=" + validationKey);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");
        return Boolean.valueOf(parseResponse(con));


    }
}
