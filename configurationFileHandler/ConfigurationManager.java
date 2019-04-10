package configurationFileHandler;

import java.io.*;
import java.util.*;

public class ConfigurationManager {
    private HashMap<String, String> parameters;
    private List<String> packs;

    public List<String> getPacks() {
        return packs;
    }

    public ConfigurationManager() {
        parameters = new HashMap<>();
        packs = new ArrayList<>();
    }

    public void loadParameters() throws IOException, NullPointerException {
        File dir = new File("src/images/");
        System.out.println("dir.getAbsolutePath() = " + dir.getAbsolutePath());
        packs = Arrays.asList(dir.list());
        ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream("src/config.dat")));
        try {

            parameters = (HashMap<String, String>) in.readObject();

            in.close();
        } catch (ClassNotFoundException e) {
            throw new IOException("file is damaged"); //yeah, this is stupid but its true, since there is a JVM error or sth
        }
    }

    public void saveKey(String key) throws IOException {
        for (Map.Entry<String, String> entry : parameters.entrySet())
            if (entry.getKey().equals("Key"))
                entry.setValue(key);

        saveAll(parameters);
    }

    public void saveAll(Map<String, String> map) throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("src/config.dat")));
        // System.out.println("map.get(\"Key\") = " + map.get("Key")); //always be sure to save your key somewhere before config reset, decompiling config.dat will cause it unreadable
        //key=YDDHO3
        // map.put("Key","000000"); //for testing key registration, edit reset key in config.dat and restart
        out.writeObject(map);
        out.close();
    }


    public String getBackgroundPath() {
        if (this.parameters.get("Theme").equals("dark"))
            return "/background dark.jpeg";
        else return "/background light.jpeg";

    }


    public String getParameter(String key) {
        return parameters.get(key);
    }


    public void saveLogin(String login) throws IOException {
        //i am pretty sure that i can stream() this
        parameters.entrySet().stream().filter(entry -> entry.getKey().equals("Login")).findFirst().get().setValue(login);

        saveAll(parameters);
    }


}

