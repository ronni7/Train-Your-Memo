package dataFlowHandler;

import utilities.enums.LEVELS;
import dataFlowHandler.entities.BestScoreEntity;
import dataFlowHandler.entities.ListViewEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.ArrayList;
public class DataExchangeManager {
    private static JSONObject jsonObject;

    public static BestScoreEntity getTopPlayerScoreAtLevel(String validationKey, String level) throws NullPointerException, IOException {
        BestScoreEntity bestScoreEntity;
        HttpURLConnection con;
        URL url;
        url = new URL("http://localhost:8080/demo/bestScoreByLevel?validationKey=" + validationKey + "&level=" + level);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");

        try {
            jsonObject = new JSONObject(parseResponse(con));
            bestScoreEntity = new BestScoreEntity(jsonObject.getString("score"), jsonObject.getString("pack"));
        } catch (JSONException e) {
            bestScoreEntity = new BestScoreEntity("00:00:00", "");
        }

        return bestScoreEntity;
    }

    private static String parseResponse(HttpURLConnection con) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);
        in.close();

        return response.toString();
    }

    public static boolean insertNewScore(Time score, String pack, String validationKey, LEVELS level) throws NullPointerException, IOException {
        HttpURLConnection con = null;
        URL url = null;
        url = new URL("http://localhost:8080/demo/add?score=" + score + "&pack=" + pack + "&validationKey=" + validationKey + "&level=" + level);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");
        return Boolean.valueOf(parseResponse(con));
    }

    public static ArrayList<ListViewEntity> loadHighscoreTable(String validationKey, String selectedLevel) throws NullPointerException, IOException, JSONException {
        HttpURLConnection con = null;
        URL url = null;
        ArrayList<ListViewEntity> list = new ArrayList<>();
        url = new URL("http://localhost:8080/demo/all?level=" + selectedLevel + "&validationKey=" + validationKey);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestMethod("GET");

        try {
            JSONArray jsonArray = new JSONArray(parseResponse(con));

            for (int i = 0; i < jsonArray.length(); i++) {
                //  System.out.println(jsonArray.getJSONObject(i).toString());
                jsonObject = jsonArray.getJSONObject(i);
                ListViewEntity lve = new ListViewEntity(i + 1 + ". " +
                        jsonObject.getString("nickname"),
                        jsonObject.getString("score"),
                        jsonObject.getString("pack"),
                        jsonObject.getString("level")

                );
                list.add(lve);
            }

        } catch (IOException e) {
            con.disconnect();
            throw e;
        } catch (JSONException e) {
            con.disconnect();
            throw e;


        }
        return list;
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
