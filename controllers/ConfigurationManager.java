package controllers;

import javafx.scene.layout.Background;

import java.awt.*;
import java.io.*;

import java.util.*;
import java.util.List;

public class ConfigurationManager {
    private File config;
    private HashMap<String, String> parameters;
    private List<String> packs;
    private Writer output;
    public List<String> getPacks() {
        return packs;
    }
    public void loadConfigFile(String filename)
    {

    }
    public ConfigurationManager() {
        config = new File("src/config.txt");
        parameters = new HashMap<>();
        packs = new ArrayList<>();


    }
    public void loadParameters() throws IOException,NullPointerException {

            BufferedReader reader = new BufferedReader(new FileReader(config));
            String line = "";


            while ((line = reader.readLine()) != null) {

                String[] params = line.split(":");
              //  System.out.println(params[0]+"  "+params[1]);
                parameters.put(params[0], params[1]);

            }




        File dir = new File("src/images/");
        packs = Arrays.asList(dir.list());
    }
    public void saveKey(String key) throws IOException
    {

          for (Map.Entry<String,String> entry: parameters.entrySet()

             ) {if(entry.getKey().equals("Key"))
                 entry.setValue(key);

        }
        System.out.println("wielkosc mapy parametrow jest   "+parameters.size());
          saveAll(parameters);
           /*   output = new BufferedWriter(new FileWriter(this.config, false));
              output.write("Key:"+key);*/

    }
    public void saveAll(Map<String,String> map)throws IOException
    {
        output = new BufferedWriter(new FileWriter(this.config, false));

            /*for (int i = 0; i < map.size(); i++) {
                output.write();
            }*/

            for (Map.Entry<String,String> entry:map.entrySet()) {
                System.out.println("i write "+ entry.getKey() +"  And  "+entry.getValue());
                output.write(entry.getKey()+":"+entry.getValue());
                ((BufferedWriter) output).newLine();
            }

            output.close();


    }

    // TODO: 2019-02-10 FileNotFound or NullPtr ? 
    public String getBackgroundPath() {
        if (this.parameters.get("Theme").equals("dark"))
            return "../background dark.jpeg";
        else return "../background light.jpeg";

    }

    // TODO: 2019-02-10 throws NullPointerException  ? 
    public String getParameter(String key)  {
        return parameters.get(key);
    }

    public File getConfig() {
        return config;
    }

    public void saveLogin(String login) throws IOException{
        for (Map.Entry<String,String> entry: parameters.entrySet()

        ) {if(entry.getKey().equals("Login"))
            entry.setValue(login);
        }
        System.out.println("wielkosc mapy parametrow po dodaniu login jest   "+parameters.size());
        saveAll(parameters);
    }
}

