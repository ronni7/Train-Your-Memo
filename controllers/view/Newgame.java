package controllers.view;

import controllers.*;
import controllers.nonView.*;
import controllers.nonView.Dialog;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Window;
import java.awt.*;
import java.io.*;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Newgame {

    @FXML
    public Label timeLabel;
    @FXML
    public BorderPane borderPaneOfTheGame;
    @FXML
    private AnchorPane paneOfTheGame;
    @FXML
    private GridPane grid;
    @FXML
    private Label timeValue;
    @FXML
    private HBox ExitHBox;
    @FXML
    private Button ExitFromGame;
    @FXML
    private HBox TimeBox;

    private MainController mainController;
    private Clock clock;
    private Board gameBoard;
    private ConfigurationManager configurationManager;
    private double imageHeight;
    private String pack;
    private LEVELS level;
    private int clickedButtonsCounter;

    public Newgame() {
        gameBoard = new Board(new int[2], new ToggleButton[2]);
        clock = new Clock();
        configurationManager = new ConfigurationManager();

    }

    public void initialize() throws NullPointerException {
        try {
            configurationManager.loadParameters();
        } catch (IOException e) {
            Back();
        }
        clock.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    clock.updateTimer();
                    timeValue.setText(clock.getTimeValue());
                });

            }
        }, 1000, 1000);
        AdjustElementsSizes();
        level = LEVELS.valueOf(configurationManager.getParameter("Level"));
        pack = configurationManager.getParameter("Pack");
        gameBoard.setSize(Integer.parseInt(configurationManager.getParameter("Size")));

        switch (gameBoard.getSize()) {
            case 16: /*{
                this.imageHeight = grid.getPrefHeight() / 8; //for 4 images in a column make 1/8 image height so they wont affect top & bottom panels
                            break;
            }*/
            case 32: {
                this.imageHeight = grid.getPrefHeight() / 8; //for 4 images in a column make 1/8 image height so they wont affect top & bottom panels
                break;
            }
            case 48: {

                this.imageHeight = grid.getPrefHeight() / 9; //for 6 images in a column make 1/9 image height so they wont affect top & bottom panels

                break;
            }
            case 64: {

                this.imageHeight = grid.getPrefHeight() / 10;  //for 8 images in a column make 1/10 image height so they wont affect top & bottom panels
                break;
            }
            default: {
                break;
            }
        }
        double imagewidth = this.imageHeight * 1.5;
        gameBoard.setBoardWidth(Integer.parseInt(configurationManager.getParameter("Width")));
        gameBoard.calculateBoardHeight();
        Image unselected = new Image(this.getClass().getResourceAsStream("/cover.jpg"), imagewidth, imageHeight, true, false);

        File dir = new File("./src/images/" + pack);
        File[] filelist;
        filelist = dir.listFiles();
        int length = 0;
        try {
            if (filelist != null) {
                length = filelist.length;
            }/* else {
                Back();
            }*/
        } catch (NullPointerException e) {
            Back();
        }
        Random r = new Random();
        int id;
        for (int i = 0; i < gameBoard.getSize() / 2; i++) {
            id = r.nextInt(length);

            while (filelist[id] == null) id = r.nextInt(length);
            //  //System.out.println("I FOUND DOUBLED! and replaced it with: " + id);
            Image temp = new Image(filelist[id].toURI().toString(), imagewidth, imageHeight, false, false);
            filelist[id] = null;

            for (int j = 0; j < 2; j++) {
                ImageView ims = new ImageView(temp);
                ToggleButton toggle = new ToggleButton();
                ims.imageProperty().bind(Bindings.when(toggle.selectedProperty()).then(temp).otherwise(unselected));
                ims.setFitHeight(imageHeight);
                ims.setFitWidth(imagewidth);
                //  System.out.println("ims.getFitHeight() = " + ims.getFitHeight());
                //System.out.println("ims.getFitWidth() = " + ims.getFitWidth());
                toggle.setGraphic(ims);

                toggle.setOnAction(event -> {
                    ToggleButton temporary = (ToggleButton) event.getSource();
                    temporary.setSelected(true);
                    int clickedID = gameBoard.GetIdFromToggle(temporary);
                    // matchedId[clickedButtonsCounter] = clickedID;
                    gameBoard.getMatchedId()[clickedButtonsCounter] = clickedID;

                    gameBoard.getButtonsMatched()[clickedButtonsCounter++] = temporary;
                    //System.out.println("Source: " + temporary.toString() +
                    //   " clickedID:  " + clickedID +
                    // "matchedID= " + matchedId[clickedButtonsCounter - 1] +
                    //" ButtonsMatched: " + buttonsMatched[clickedButtonsCounter - 1] +
                    //"clickedButtonsCounter: " + (clickedButtonsCounter - 1));
                    isMatched(temporary);
                });
                BoardNode boardNode = new BoardNode(toggle, id);
                gameBoard.getList().add(boardNode);
            }
        }
        Collections.shuffle(gameBoard.getList());
        //  System.out.println(grid.getWidth()+"act- pref:"+grid.getPrefWidth()+"act"+grid.getHeight()+"pref"+grid.getPrefHeight()+"Hgap"+grid.getHgap()+"Vgap"+grid.getVgap());
        int elements = gameBoard.getList().size() - 1;
        for (int j = 0; j < gameBoard.getBoardHeight(); j++)
            for (int k = 0; k < gameBoard.getBoardWidth(); k++)
                grid.add(gameBoard.getList().get(elements--).getToggle(), k, j);
    }

    private void AdjustElementsSizes() {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        double width = graphicsDevice.getDisplayMode().getWidth();
        double height = graphicsDevice.getDisplayMode().getHeight();
        paneOfTheGame.setPrefSize(width, height);
        borderPaneOfTheGame.setPrefSize(width, 0.9 * height);
        borderPaneOfTheGame.setLayoutY(0.05 * height);
        grid.setPrefSize(0.8 * width, 0.8 * height);
        grid.setLayoutX(0.1 * width);
        grid.setLayoutY(0.1 * height);
        ExitHBox.setPrefSize(width * 0.2, 0.05 * height);
        ExitHBox.setLayoutX(0.4 * width);
        ExitHBox.setLayoutY(0.925 * height);
        ExitFromGame.setPrefSize(ExitHBox.getPrefWidth(), ExitHBox.getPrefHeight());
        ExitFromGame.setLayoutX(0.5 * width - (0.0625) * width);
        ExitFromGame.setLayoutY(0.925 * height);
        TimeBox.setPrefSize(ExitHBox.getPrefWidth(), ExitHBox.getPrefHeight());
        TimeBox.setLayoutX(0.5 * width);
        TimeBox.setLayoutY(0.025 * height);
        timeValue.setPrefSize(TimeBox.getPrefWidth(), TimeBox.getPrefHeight());
        timeLabel.setPrefSize(TimeBox.getPrefWidth(), TimeBox.getPrefHeight());

    }

    private boolean checkWin() {

        if (gameBoard.win()) {
            clock.stopTime();
            return true;
        }
        return false;
    }

    private void isMatched(ToggleButton temporary) {
        //lock and unlock with reenrtrant lock...lock
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        if (clickedButtonsCounter == 2) {
            clickedButtonsCounter = 0;
            grid.setMouseTransparent(true);
            final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
            if (gameBoard.getMatchedId()[0] == gameBoard.getMatchedId()[1] && gameBoard.getButtonsMatched()[0] != gameBoard.getButtonsMatched()[1]) { //matched

                executor.schedule(() -> {
                    gameBoard.DisableByID(gameBoard.getMatchedId()[0]);
                    grid.setMouseTransparent(false);
                    if (checkWin()) {
                        Platform.runLater(new Thread(() -> {
                            if (showDialog(mainController.getBackgroundManager().getLoadedBackground(), paneOfTheGame.getScene().getWindow(), DIALOGTYPE.Saving))
                                try {
                                    if (DataExchangeManager.insertNewScore(Time.valueOf("00:" + timeValue.getText()), pack, configurationManager.getParameter("Key"), this.level)){
                                        Dialog dialog = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), paneOfTheGame.getScene().getWindow(), DIALOGTYPE.Information);
                                        dialog.setTitle("Success");
                                        dialog.setHeaderText("Your result has been successfully signed!");
                                        dialog.showDialog();
                                    }
                                    Back();
                                } catch (IOException e) {
                                    Dialog errodDialog = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), paneOfTheGame.getScene().getWindow(), DIALOGTYPE.Information);
                                    errodDialog.setTitle("Error");
                                    errodDialog.setHeaderText("An error occurred while connecting server. Please check your connection");
                                    errodDialog.showDialog();
                                    Back();
                                }
                            else Back();
                        }));
                    }
                    lock.unlock();
                    grid.setMouseTransparent(false);
                }, 700, TimeUnit.MILLISECONDS);

            } else {
                executor.schedule(() -> {
                    gameBoard.unselectToggleById(gameBoard.getMatchedId()[0]);
                    gameBoard.clearTables();
                    temporary.setSelected(false);
                    grid.setMouseTransparent(false);
                }, 700, TimeUnit.MILLISECONDS);
            }

        }

    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void Back() {
        this.mainController.loadMainScreen();
    }

    private boolean showDialog(Background background, Window window, DIALOGTYPE dialogtype) {
        Dialog dialog = new Dialog(background, window, dialogtype);
        dialog.setTitle("congratulations");
        dialog.setHeaderText("Do you want to sign your result ?");
        dialog.showDialog();
        return dialog.getResult();
    }

    public void setConfigurationManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }
}

