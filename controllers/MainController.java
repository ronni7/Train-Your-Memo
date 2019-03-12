package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.io.IOException;

public class MainController {
    @FXML
    private StackPane mainStackPane= new StackPane();
    private AudioManager audioManager;
    private BackgroundManager backgroundManager;




private ConfigurationManager configurationManager = new ConfigurationManager();
//    private FontManager fontManager;

    public BackgroundManager getBackgroundManager() {
        return backgroundManager;
    }

    @FXML
    public void initialize() {
    /*    try {
            configurationManager.loadParameters();
        } catch (IOException e) {
            System.out.println("kill me");
        }*/
        loadMainScreen();

        //     firstRun = false;
        //      System.out.println(this.firstRun);
    }



    public void loadMainScreen() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/menu.fxml"));
        Pane pane = null;


        try {
            configurationManager.loadParameters();

        } catch (IOException e) {

            Alert a=new Alert(Alert.AlertType.ERROR,"Error while loading configuration please check your files and restart or reinstall game",ButtonType.OK);
                   a.setTitle("File error");
                   a.showAndWait();
                   System.exit(-1);
        }
        //   this.fontManager = new FontManager(50);

     /*   if (firstRun) {
            audioManager = new AudioManager();
            System.out.println(this.firstRun + " is first run");
        }*/
        if (Boolean.valueOf(configurationManager.getParameter("Music"))) {
            if (this.audioManager == null)
                audioManager = new AudioManager();
        } else {
            if (audioManager != null)
                this.audioManager.stop();
            this.audioManager = null;
        }

        try {
         //   System.out.println(configurationManager==null);
            configurationManager.loadParameters();

            pane = loader.load();
            this.backgroundManager = new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath());
            Background back = backgroundManager.getLoadedBackground();

            if (back != null)
                mainStackPane.setBackground(back);

        } catch (IOException e) {
          //  System.out.println("YOu sucks");
            Alert a=new Alert(Alert.AlertType.ERROR,"Error while loading configuration please check your files and restart or reinstall game",ButtonType.OK);
            a.setTitle("File loading error");
            a.showAndWait();
            System.exit(-1);
        }
        catch (NullPointerException e) {
            Alert a=new Alert(Alert.AlertType.ERROR,"Error while loading configuration please check your files and restart or reinstall game",ButtonType.OK);
            a.setTitle("File loading error");
            a.showAndWait();
            System.exit(-1);
        }

        Menu menuController = loader.getController();
        //  menuController.setFontManager(fontManager);
        menuController.setConfigurationManager(configurationManager);
        menuController.setMainController(this);

        setScreen(pane);
    }
    public void setScreen(Pane pane) {

        mainStackPane.getChildren().clear();
        mainStackPane.getChildren().add(pane);


    }

}
