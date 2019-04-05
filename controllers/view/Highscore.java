package controllers.view;

import controllers.*;
import controllers.nonView.BestScoreEntity;
import controllers.nonView.DataExchangeManager;
import controllers.nonView.ListViewEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import org.json.JSONException;


import java.awt.*;
import java.io.IOException;

import java.util.ArrayList;


public class Highscore {
    @FXML
    private VBox selectionVBox;
    @FXML
    private VBox scoreVBox;
    @FXML
    private Pane paneOfTheHighscores;
    @FXML
    private ListView<ListViewEntity> table;
    @FXML
    private Label levelLabel;
    @FXML
    private Label bestTime;
    @FXML
    private ChoiceBox<String> level;
    @FXML
    private Label bestScore;
    @FXML
    private Label packLabel;
    private MainController mainController;
    private ObservableList list = FXCollections.observableArrayList();
    private ArrayList<ListViewEntity> listViewData = new ArrayList<>();

    // TODO: 2019-03-07   is this needed ?
    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }
    public void setConfigurationManager(ConfigurationManager configurationManager) {
    }

    private ConfigurationManager configurationManager = new ConfigurationManager();

    @FXML
    public void Back() {
        setMainController(mainController);
        mainController.loadMainScreen();
    }

    public void initialize() throws IOException {

        configurationManager.loadParameters();
        level.setTooltip(new Tooltip("Beginner (8 pairs),Average ( 16 pairs),Expert ( 24 pairs ),Master ( 32 pairs )"));
        bestTime.setTooltip(new Tooltip("Your best time at this level"));
        list.removeAll(list);
        list.addAll("Beginner", "Average", "Expert", "Master");
        level.getItems().addAll(list);
        level.getSelectionModel().selectFirst();
        try {
            level.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                try {
                  updateTable();
                } catch (IOException e) {
                    Back();

                }
                updateBestTime();
            });
            updateTable();
        } catch (IOException e) {

            Back();

        }
    }

    private void updateBestTime() {
        try {
            configurationManager.loadParameters();
        } catch (IOException e) {
            Back();
        }
        BestScoreEntity bestScoreEntity = null;
        try {
            bestScoreEntity = DataExchangeManager.getTopPlayerScoreAtLevel(configurationManager.getParameter("Key"), level.getValue());
        } catch (NullPointerException e) {
            bestScoreEntity = new BestScoreEntity("00:00:00", "");
            Back();
        } catch (IOException e) {
            bestScoreEntity = new BestScoreEntity("00:00:00", "");

            Back();

        }
        bestTime.setText(bestScoreEntity.getScore());
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void AdjustElementsSizes() {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        double width = graphicsDevice.getDisplayMode().getWidth();
        double height = graphicsDevice.getDisplayMode().getHeight();
        paneOfTheHighscores.setPrefSize(width, height);
        selectionVBox.setPrefSize(0.2 * width, 0.7 * height);
        selectionVBox.setLayoutX(0.10 * width);
        selectionVBox.setLayoutY(0.15 * height);
        selectionVBox.setAlignment(Pos.CENTER);
        selectionVBox.setSpacing(0.05 * selectionVBox.getPrefHeight());
        scoreVBox.setPrefSize(0.35 * width, 0.9 * height);
        scoreVBox.setLayoutX(0.325 * width);
        scoreVBox.setLayoutY(0.05 * height);
        scoreVBox.setSpacing(0.025 * scoreVBox.getPrefHeight());
        bestScore.setPrefWidth(scoreVBox.getPrefWidth());
        bestScore.setWrapText(true);
        bestTime.setWrapText(true);
        levelLabel.setWrapText(true);
    }

    public void loadData() throws NullPointerException {

        try {
            configurationManager.loadParameters();
        } catch (IOException e) {
            Back();
        }
        updateBestTime();
        AdjustElementsSizes();


    }


    public void updateTable() throws NullPointerException, IOException {
        ArrayList<ListViewEntity> listViewEntities;
        configurationManager.loadParameters();
        try {
            listViewEntities = DataExchangeManager.loadHighscoreTable(configurationManager.getParameter("Key"), level.getValue());
        } catch (JSONException e) {
            listViewEntities = new ArrayList<>();
        }
        listViewData.clear();
        table.getItems().clear();
        table.refresh();
        listViewData.addAll(listViewEntities);

        if (listViewData.size() > 0) {
            table.getItems().addAll(listViewData);
            table.refresh();
            table.setEditable(false);
                 }
    }
}


