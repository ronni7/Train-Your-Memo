package controllers;

import javafx.scene.layout.Background;

import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class ConfigurationManager{
    private HashMap<String, String> parameters;
    private List<String> packs;

    //    private Writer output;
    public List<String> getPacks() {
        return packs;
    }

    public ConfigurationManager() {
        parameters = new HashMap<>();
        packs = new ArrayList<>();

           }

    public void loadParameters() throws IOException, NullPointerException {


    /*   BufferedReader reader = new BufferedReader(new FileReader(config));
        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] params = line.split(":");
            parameters.put(params[0], params[1]);
        }*/
        File dir = new File("src/images/");
        packs = Arrays.asList(dir.list());
        ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream("src/config.dat")));
        try {

parameters=(HashMap<String, String>) in.readObject();

                      in.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
   }

    public void saveKey(String key) throws IOException {
        for (Map.Entry<String, String> entry : parameters.entrySet())
            if (entry.getKey().equals("Key"))
                entry.setValue(key);
        // System.out.println("wielkosc mapy parametrow jest   " + parameters.size());
        saveAll(parameters);
    }

    public void saveAll(Map<String, String> map) throws IOException {
      /*  BufferedWriter output = new BufferedWriter(new FileWriter(this.config, false));

        for (Map.Entry<String, String> entry : map.entrySet()) {
            //   System.out.println("i write " + entry.getKey() + "  And  " + entry.getValue());
            output.write(entry.getKey() + ":" + entry.getValue());
            output.newLine();
        }
        output.close();*/
        ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("src/config.dat")));
        map.put("Key","000000");
        out.writeObject(map);

        out.close();
         }


    public String getBackgroundPath() {
        if (this.parameters.get("Theme").equals("dark"))
            return "../background dark.jpeg";
        else return "../background light.jpeg";

    }


    public String getParameter(String key) {
        return parameters.get(key);
    }


    public void saveLogin(String login) throws IOException {
        for (Map.Entry<String, String> entry : parameters.entrySet())
            if (entry.getKey().equals("Login"))
                entry.setValue(login);

        saveAll(parameters);
    }


}

