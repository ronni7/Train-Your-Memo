package controllers;

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

    private MainController mainController;
    private ObservableList list = FXCollections.observableArrayList();
    private ArrayList<ListViewEntity> listViewData = new ArrayList<>();


    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    public void setConfigurationManager(ConfigurationManager configurationManager) {
    /*    System.out.println(configurationManager!=null);
        this.configurationManager = configurationManager;
        try {
            this.configurationManager.loadParameters();
        } catch (IOException e) {
                     e.printStackTrace();
        }*/
    }

    private ConfigurationManager configurationManager=new ConfigurationManager();

    @FXML
    public void Back() {
        setMainController(mainController);
        //  System.out.println(mainController==null);
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
                    this.updateTable();
                } catch (IOException e) {
                //    System.out.println("THIS SUCKS");
                }
                this.updateBestTime();
            });
            this.updateTable();
        } catch (IOException e) {
           // System.out.println("THIS SUCKS MORE EVEN");
            Back();
          //  e.printStackTrace();
        }

    }

    private void updateBestTime() {
        try {
            configurationManager.loadParameters();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BestScoreEntity bestScoreEntity = null;
        try {
            bestScoreEntity = DataExchangeManager.getTopPlayerScoreAtLevel(configurationManager.getParameter("Login"),configurationManager.getParameter("Key"), level.getValue());

        } catch (NullPointerException e) {
          //  System.out.println("THIS SUCKS AS HELL");

          bestScoreEntity=new BestScoreEntity("00:00:00","");
         /*   Dialog dialog = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), packLabel.getScene().getWindow(), DIALOGTYPE.Information);
            dialog.setTitle("Error");
            dialog.setHeaderText("An error occurred while loading data from server. Please check your connection");
            dialog.showDialog();*/
            Back();
        } catch (IOException e) {
            bestScoreEntity=new BestScoreEntity("00:00:00","");

          //  System.out.println("THIS SUCKS AS HELL 3");
            Back();
            //   e.printStackTrace();
        }
        this.bestTime.setText(bestScoreEntity.getScore());


    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void AdjustElementsSizes() {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        double width = graphicsDevice.getDisplayMode().getWidth();
        double height = graphicsDevice.getDisplayMode().getHeight();

        paneOfTheHighscores.setPrefSize(width, height); //because options don't really need fullscreen tho
        this.selectionVBox.setPrefSize(0.2 * width, 0.7 * height);
        this.selectionVBox.setLayoutX(0.10 * width);
        this.selectionVBox.setLayoutY(0.15 * height);
        this.selectionVBox.setAlignment(Pos.CENTER);
        //    this.selectionVBox.setPadding(new Insets(0.15*selectionVBox.getPrefWidth()));
        this.selectionVBox.setSpacing(0.05 * selectionVBox.getPrefHeight());
        this.scoreVBox.setPrefSize(0.35 * width, 0.9 * height);
        this.scoreVBox.setLayoutX(0.325 * width);
        this.scoreVBox.setLayoutY(0.05 * height);
        this.scoreVBox.setSpacing(0.025 * scoreVBox.getPrefHeight());
        this.bestScore.setPrefWidth(scoreVBox.getPrefWidth());
        this.bestScore.setWrapText(true);
        this.bestTime.setWrapText(true);
        this.levelLabel.setWrapText(true);
    }

    public void loadData() throws NullPointerException {

        try {
            configurationManager.loadParameters();
        } catch (IOException e) {
            System.out.println("blondol");
         Back();
        }
        updateBestTime();
        AdjustElementsSizes();


    }


    public void updateTable() throws NullPointerException, IOException {
        ArrayList<ListViewEntity> listViewEntities;
        configurationManager.loadParameters();
        try {
            listViewEntities = DataExchangeManager.loadHighscoreTable(configurationManager.getParameter("Login"), configurationManager.getParameter("Key"),level.getValue());
        }
        catch (JSONException e)
        {
         listViewEntities=new ArrayList<>();
                 }
        listViewData.clear();
        table.getItems().clear();
        table.refresh();


        listViewData.addAll(listViewEntities);

        if (listViewData.size() > 0) {
            table.getItems().addAll(listViewData);
            table.refresh();
            table.setEditable(false);
            // }
        }


    }
}


