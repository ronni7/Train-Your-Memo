package controllers.view;

import configurationFileHandler.ConfigurationManager;
import utilities.enums.DIALOGTYPE;
import customDialogs.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class Options {
    @FXML
    public Pane paneOfOptions;
    @FXML
    public HBox levelHBox;
    @FXML
    public HBox musicHBox;
    @FXML
    public HBox saveHBox;
    @FXML
    public HBox mainMenuHBox;
    @FXML
    public HBox themeHBox;
    @FXML
    public HBox packHBox;
    @FXML
    public Label levelLabel;

    private MainController mainController;
    private final ObservableList list = FXCollections.observableArrayList();
    private final ObservableList packsAvailable = FXCollections.observableArrayList();
    private final int[] size = {16, 32, 48, 64};
    private final int[] width = {4, 8, 8, 8};

    public void setConfigurationManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    private ConfigurationManager configurationManager;

    @FXML
    public ChoiceBox pack;
    @FXML
    private ToggleButton theme;
    @FXML
    private CheckBox music;
    @FXML
    private ChoiceBox<String> level;

    @FXML
    public boolean MusicChanged() {
        if (this.music.isSelected()) {
            this.music.setText("On  ");//Music:
            return true;
        } else {
            this.music.setText("Off");//Music:
            return false;
        }
    }

    @FXML
    public String Toggled() {
        if (this.theme.isSelected()) {
            this.theme.setText("Dark");//Theme:

            return "dark";
        } else {
            this.theme.setText("Light");//Theme:
            return "light";
        }
    }

    private void save() {
        Map<String, String> map = new HashMap<>();
        map.put("Size", String.valueOf(size[list.indexOf(level.getValue())]));
        map.put("Width", String.valueOf(width[list.indexOf(level.getValue())]));
        map.put("Music", String.valueOf(this.MusicChanged()));
        map.put("Theme", this.Toggled());
        map.put("Level", this.level.getValue());
        map.put("Pack", this.pack.getValue().toString());
        try {
            map.put("Key", configurationManager.getParameter("Key"));
            map.put("Login", configurationManager.getParameter("Login"));
            configurationManager.saveAll(map);
            Back();
        } catch (IOException e) {
            Dialog dialog = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), music.getScene().getWindow(), DIALOGTYPE.Information);
            dialog.setTitle("Error");
            dialog.setHeaderText("An error occurred while saving . Please check your files or reinstall game");
            dialog.showDialog();
            if (dialog.getResult())
                Back();
            System.exit(-1);
        }
    }

    @FXML
    public void Back() {

        mainController.loadMainScreen();
    }

    @FXML
    public void showDialog() {
           Dialog dialog = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), music.getScene().getWindow(), DIALOGTYPE.Saving);
        dialog.setTitle("Settings changed");
        dialog.setHeaderText("Do you wish to save changes?");
        dialog.showDialog();
        if (dialog.getResult())
            save();
    }

    private void adjustElementsSizes() {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        double width = graphicsDevice.getDisplayMode().getWidth();
        double height = graphicsDevice.getDisplayMode().getHeight();
        paneOfOptions.setPrefSize(width, height); //because options don't really need fullscreen tho
        this.levelHBox.setPrefSize(0.5 * width, 0.18 * height);
        this.levelHBox.setLayoutX(0.25 * width);
        this.levelHBox.setLayoutY(0.09 * height);
        this.packHBox.setPrefSize(levelHBox.getPrefWidth(), levelHBox.getPrefHeight());
        this.packHBox.setLayoutX(0.25 * width);
        this.packHBox.setLayoutY(0.27 * height);
        this.musicHBox.setPrefSize(levelHBox.getPrefWidth(), levelHBox.getPrefHeight());
        this.musicHBox.setLayoutX(0.25 * width);
        this.musicHBox.setLayoutY(0.45 * height);
        this.themeHBox.setPrefSize(levelHBox.getPrefWidth(), levelHBox.getPrefHeight());
        this.themeHBox.setLayoutX(0.25 * width);
        this.themeHBox.setLayoutY(0.63 * height);
        this.saveHBox.setPrefSize(levelHBox.getPrefWidth() * 0.5, levelHBox.getPrefHeight());
        this.saveHBox.setLayoutX(0.25 * width);
        this.saveHBox.setLayoutY(0.81 * height);
        this.mainMenuHBox.setPrefSize(levelHBox.getPrefWidth() * 0.5, levelHBox.getPrefHeight());
        this.mainMenuHBox.setLayoutX(0.5 * width);
        this.mainMenuHBox.setLayoutY(0.81 * height);
    }


    public void loadData() {
        try {
            configurationManager.loadParameters();
        } catch (IOException e) {
            Dialog dialog = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), music.getScene().getWindow(), DIALOGTYPE.Information);
            dialog.setTitle("Error");
            dialog.setHeaderText("An error occurred while loading config file . Please check your files or reinstall game");
            dialog.showDialog();
            if (dialog.getResult())
                Back();
        }
        adjustElementsSizes(); //here cause in initialize it causes a crash on dialog when it is refreshing form
        packsAvailable.removeAll(); //arg packsAvailable
        list.removeAll(); //arg list
        packsAvailable.addAll(configurationManager.getPacks());
        pack.getItems().addAll(packsAvailable);
        pack.setTooltip(new Tooltip("Select pack which you want to play with "));
        list.addAll("Beginner", "Average", "Expert", "Master");
        level.getItems().addAll(list);
        level.setTooltip(new Tooltip("Beginner (8 pairs),Average ( 16 pairs),Expert ( 24 pairs ),Master ( 32 pairs )"));
        for (int i = 0; i < size.length; i++) {
            if (size[i] == Integer.parseInt(configurationManager.getParameter("Size")))
                level.getSelectionModel().select(i);
        }
        for (Object s : packsAvailable) {
            if (s.toString().equals(configurationManager.getParameter("Pack")))
                this.pack.getSelectionModel().select(s);

        }

        if (configurationManager.getParameter("Theme").equals("dark")) {
            this.theme.setSelected(true);

            this.theme.setText("Dark");
        } else {
            this.theme.setSelected(false);
            this.theme.setText("Light");

        }
        if (Boolean.valueOf(configurationManager.getParameter("Music"))) {
            this.music.setSelected(true);
            this.music.setText("On");

        } else {
            this.music.setSelected(false);
            this.music.setText("Off");

        }
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
