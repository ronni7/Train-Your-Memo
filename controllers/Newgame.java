package controllers;

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

    private boolean endflag = false;
    private MainController mainController;
    private Timer timer;
    private int time = 1;
    private int minutes = 0;
    private int boardHeight;
    private int boardWidth;
    private Board gameBoard;
    private int size;
    private Image unselected;
    private int counter;
    private int[] matchedId;
    private ToggleButton[] buttonsMatched;
    private ConfigurationManager configurationManager=new ConfigurationManager() ;
    private double imagewidth;
    private double imageheight;
    private String pack;
    private LEVELS level;

    public Newgame() {
        gameBoard = new Board();

        matchedId = new int[2];
        buttonsMatched = new ToggleButton[2];


    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setMatchedId(int[] matchedId) {
        this.matchedId = matchedId;
    }



    public void setTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    String single, doubl;
                    if (time < 10) single = "0";
                    else single = "";
                    if (minutes < 10) doubl = "0";
                    else doubl = "";
                    if (time >= 0) {
                      //  timeValue.setText(doubl + String.valueOf(minutes) + ":" + single + String.valueOf(time));
                        timeValue.setText(doubl + minutes + ":" + single + time);
                        time++;
                        if (time % 60 == 0) {
                            minutes++;
                            time = 0;
                        }
                    } else {
                        timer.cancel();
                      //  System.out.println(timeValue.getText());
                    }
                });

            }
        }, 1000, 1000);
    }

    public void initialize() throws NullPointerException{

        // TODO: 2019-02-03 sizing resizing game board, images fit to the buttons & stuff
        try {
            configurationManager.loadParameters();
        } catch (IOException e) {
            Back();
        }
        setTimer();
        AdjustElementsSizes();
        level = LEVELS.valueOf(configurationManager.getParameter("Level"));
        pack = configurationManager.getParameter("Pack");
        this.setSize(Integer.parseInt(configurationManager.getParameter("Size")));
        switch (this.getSize()) {
            case 16: {
                this.imageheight = 100;
                break;
            }
            case 32: {
                this.imageheight = 80;
                break;
            }
            case 48: {
                this.imageheight = 60;
                break;
            }
            case 64: {
                this.imageheight = 40;
                break;
            }
            default: {
                break;
            }
        }
        this.setBoardWidth(Integer.parseInt(configurationManager.getParameter("Width")));

        this.setBoardHeight(this.getSize() / this.getBoardWidth());
        this.setCounter(0);
        unselected = new Image(this.getClass().getResourceAsStream("/cover.jpg"), imageheight * 1.5, imageheight, true, false);
        this.setMatchedId(new int[]{0, 0});
        //  imagewidth = grid.getPrefWidth() /this.getBoardWidth() ;
        // imageheight = grid.getPrefHeight()/this.getBoardHeight();
        imagewidth = 150;
        imageheight = 100;
      

    /*    try {
            imagewidth /= levelratio;
            imageheight /= levelratio;
            this.setUnselected(
                    new Image(this.getClass().getResourceAsStream("/cover.jpg")
                            , imagewidth
                            , imageheight,
                            true,
                            false));


        } catch (ArithmeticException e) {
            imagewidth = 150;
            imageheight = 1000;
          //  e.printStackTrace();
        }*/
        File dir = new File("./src/images/" + pack);

        File[] filelist;
        filelist = dir.listFiles();
        int length = 0;
        try {
            if (filelist != null) {
                length = filelist.length;
            } else {
                Back();
            }
        } catch (NullPointerException e) {
            //System.out.println(e.getMessage()); 
            // TODO: 2019-02-06 check files/reinstall message 
            Back();
        }
        Random r = new Random();
        int id;


        for (int i = 0; i < size / 2; i++) {
            id = r.nextInt(length);

            while (filelist[id] == null) {
                id = r.nextInt(length);
                //  //System.out.println("I FOUND DOUBLED! and replaced it with: " + id);
            }
            Image temp = new Image(filelist[id].toURI().toString(), imagewidth*1.5, imageheight, false, false);
            filelist[id] = null;


            for (int j = 0; j < 2; j++) {
                ImageView ims = new ImageView(temp);
             //   ims.setFitWidth(150);
             //   ims.setFitHeight(100);
                ToggleButton toggle = new ToggleButton();

                ims.imageProperty().bind(Bindings.when(toggle.selectedProperty()).then(temp).otherwise(this.unselected));
                ims.setFitHeight(0.95 * this.imageheight);
                ims.setFitWidth(0.95 * this.imagewidth);
                toggle.setGraphic(ims);


                toggle.setOnAction(event -> {

                    ToggleButton temporary = (ToggleButton) event.getSource();
                    temporary.setSelected(true);
                    int clickedID = gameBoard.GetIdFromToggle(temporary);
                    matchedId[counter] = clickedID;
                    buttonsMatched[counter++] = temporary;
                    //System.out.println("Source: " + temporary.toString() +
                    //   " clickedID:  " + clickedID +
                    // "matchedID= " + matchedId[counter - 1] +
                    //" ButtonsMatched: " + buttonsMatched[counter - 1] +
                    //"counter: " + (counter - 1));

                    isMatched(temporary);
                  /*  if (isMatched(temporary))
                        if (checkWin()) {
                            Platform.runLater(new Thread(() -> showDialog()));
                            Back();
                        }*/


                });
                BoardNode boardNode = new BoardNode(toggle, id);
                gameBoard.list.add(boardNode);
            }
        }

        Collections.shuffle(gameBoard.list);
      //  System.out.println(grid.getWidth()+"act- pref:"+grid.getPrefWidth()+"act"+grid.getHeight()+"pref"+grid.getPrefHeight()+"Hgap"+grid.getHgap()+"Vgap"+grid.getVgap());
        int elements = gameBoard.list.size() - 1;
        for (int j = 0; j < this.getBoardHeight(); j++) {
            for (int k = 0; k < this.getBoardWidth(); k++) {
                // if (this.getBoardHeight() == 4 &&  4==this.getBoardWidth())
                grid.add(gameBoard.list.get(elements--).getToggle(), k, j);


            }
        }
     //   System.out.println(grid.getWidth()+"act- pref:"+grid.getPrefWidth()+"act"+grid.getHeight()+"pref"+grid.getPrefHeight()+"Hgap"+grid.getHgap()+"Vgap"+grid.getVgap());
    }

    private void AdjustElementsSizes() {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        double width = graphicsDevice.getDisplayMode().getWidth();
        double height = graphicsDevice.getDisplayMode().getHeight();

        paneOfTheGame.setPrefSize(width, height);
        borderPaneOfTheGame.setPrefSize(width,0.9*height);
        borderPaneOfTheGame.setLayoutY(0.05*height);
        grid.setPrefSize(0.8 * width, 0.8 * height);
        grid.setLayoutX(0.1 * width);
        grid.setLayoutY(0.1 * height);
        // TODO: 2019-02-06 setting sizes of images

        ExitHBox.setPrefSize(width * 0.2, 0.05 * height);
        ExitHBox.setLayoutX(0.4 * width );
        ExitHBox.setLayoutY(0.925 * height);

        ExitFromGame.setPrefSize(ExitHBox.getPrefWidth(), ExitHBox.getPrefHeight());
        ExitFromGame.setLayoutX(0.5 * width - (0.0625) * width);
        ExitFromGame.setLayoutY(0.925 * height);
        TimeBox.setPrefSize(ExitHBox.getPrefWidth(), ExitHBox.getPrefHeight());
        TimeBox.setLayoutX(0.5 * width);
        TimeBox.setLayoutY(0.025 * height);
        timeValue.setPrefSize(TimeBox.getPrefWidth(), TimeBox.getPrefHeight());
        timeLabel.setPrefSize(TimeBox.getPrefWidth(), TimeBox.getPrefHeight());
   //     TimeLabelBox.setPrefSize(ExitHBox.getPrefWidth(), ExitHBox.getPrefHeight());
    //    TimeLabelBox.setLayoutX(0.5 * width);
    //    TimeLabelBox.setLayoutY(0.025 * height);
   //     emptylabel.setPrefSize(TimeLabelBox.getPrefWidth(), TimeLabelBox.getPrefHeight());
    }

    private boolean checkWin() {

        if (gameBoard.win()) {
          //  System.out.println("you won");
            time = -1;
           // this.endflag = true;
            // HiddenDialogButton.fireEvent(Event onAction);
            return true;
        }
        return false;
    }


    private void isMatched(ToggleButton temporary) {
     //   boolean flag = false;
        //lock and unlock with reenrtrant lock...lock
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        if (getCounter() == 2) {
            grid.setMouseTransparent(true);
            if (matchedId[0] == matchedId[1] && buttonsMatched[0] != buttonsMatched[1]) { //matched
                //flag = true;
                setCounter(0);
                final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
                executor.schedule(() -> {
                    gameBoard.DisableByID(matchedId[0]);
                    grid.setMouseTransparent(false);
                    if (checkWin()) {
                        Platform.runLater(new Thread(() -> {
                            if (showDialog(mainController.getBackgroundManager().getLoadedBackground(), paneOfTheGame.getScene().getWindow(), DIALOGTYPE.Saving))
                                try {
                                    DataExchangeManager.insertNewScore(
                                            configurationManager.getParameter("Login"), Time.valueOf("00:"+timeValue.getText()), this.pack, configurationManager.getParameter("Key"), this.level);
                                    Back();
                                } catch (IOException e) {
                                    Dialog dialogx = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), paneOfTheGame.getScene().getWindow(), DIALOGTYPE.Information);
                                    dialogx.setTitle("Error");
                                    dialogx.setHeaderText("An error occurred while connecting server. Please check your connection");
                                    dialogx.showDialog();
                                    Back();
                                }
                        }));

              //      executor.schedule(()-> new Thread(() -> showDialog()),0,TimeUnit.MILLISECONDS);
                        lock.unlock();
                    }
                    lock.unlock();
                    grid.setMouseTransparent(false);

                }, 700, TimeUnit.MILLISECONDS);

            }/* else if (matchedId[0] != matchedId[1]) { //not matched, different images were picked
                setCounter(0);

                final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
                executor.schedule(() -> {

                    gameBoard.unselectToggleById(matchedId[0]);
                    clearTables();
                    temporary.setSelected(false);

                    grid.setMouseTransparent(false);
                    //  System.out.println(Thread.currentThread().getName() + "run2");
                }, 700, TimeUnit.MILLISECONDS);
            }*/ else {
                setCounter(0);

                final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
                executor.schedule(() -> {

                    gameBoard.unselectToggleById(matchedId[0]);
                    clearTables();
                    temporary.setSelected(false);

                    grid.setMouseTransparent(false);
                    //  System.out.println(Thread.currentThread().getName() + "run2");
                }, 700, TimeUnit.MILLISECONDS);
            }
            //return flag;
        }
      //  return false;
    }


    private void clearTables() {
        for (int i = 0; i < 2; i++) {
            matchedId[i] = 0;
            buttonsMatched[i] = null;
        }
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void Back() {
        if (this.endflag) {
            this.endflag = false;
           // showDialog();

        }
        mainController.loadMainScreen();
    }
    private boolean showDialog(Background b, Window w, DIALOGTYPE d) {
        Dialog dialog = new Dialog(b, w, d);
        dialog.setTitle("congratulations");
        dialog.setHeaderText("Do you want to sign your result ?");
        dialog.showDialog();
        return dialog.getResult();
    }
/*    public  void showDialog() {
   *//*     TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Congratulations!!!");
        dialog.setHeaderText("You have done it!");
        dialog.setContentText("Please enter your name to sign your result.");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            this.username = result.get();
            System.out.println(this.username);

            //    DataExchangeManager.insertNewScore(new Player(this.username,this.timeLabel.getText()));
        }*//*
        Dialog dialog = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), paneOfTheGame.getScene().getWindow(), DIALOGTYPE.Saving);
        dialog.setTitle("congratulations");
        dialog.setHeaderText("Do you want to sign your result ?");
        dialog.showDialog();
        if (dialog.getResult()) {
            try {
                DataExchangeManager.insertNewScore(timeValue.getText(), this.pack, configurationManager.getParameter("Key"), this.level);
            } catch (IOException e) {
                Dialog dialogx =new Dialog(mainController.getBackgroundManager().getLoadedBackground(),paneOfTheGame.getScene().getWindow(),DIALOGTYPE.Information);
                dialogx.setTitle("Error");
                dialogx.setHeaderText("An error occurred while connecting server. Please check your connection");
                dialogx.showDialog();
              //  if(dialogx.getResult())
                  Back();
            }
        }

    }*/



    public void setConfigurationManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }
}

