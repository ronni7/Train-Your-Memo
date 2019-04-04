package controllers;

import controllers.nonView.DIALOGTYPE;
import controllers.nonView.Dialog;
import controllers.view.Credits;
import controllers.view.Highscore;
import controllers.view.Newgame;
import controllers.view.Options;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

// TODO: 2018-12-17  naprawic przekazywanie instancji pliku konfiguracyjnego  
public class Menu {
    @FXML
    public GridPane menuGrid;
    private controllers.MainController mainController;
    private controllers.ConfigurationManager configurationManager;
//    private FontManager fontManager;

    //  public void setFontManager(FontManager fontManager) {
    //      this.fontManager = fontManager;
    //  }
    public void showErrorDialog(String errorTitle, String errorHeader) {
        Dialog dialog = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Information);
        dialog.setTitle(errorTitle);
        dialog.setHeaderText(errorHeader);
        dialog.showDialog();


    }

    public void setConfigurationManager(controllers.ConfigurationManager configurationManager) {

        this.configurationManager = configurationManager;
        try {
            configurationManager.loadParameters();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorDialog("Error", "An error occurred while loading game files . Please check your files and try again or reinstall game");

        }
    }

    public void setMainController(controllers.MainController mainController) {
      /*  if(configurationManager !=null)
            System.out.println("dziala 2 ");
        else
            System.out.println("nie dziala");
*/
        this.mainController = mainController;
    }


    @FXML
    public void newGame() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/newgame.fxml"));
        Pane pane = null;
        try {

            pane = loader.load();

        } catch (IOException e) {
            //      System.out.println("How to load problems");
            showErrorDialog("Error", "An error occurred while loading game files . Please check your files and try again or reinstall game");
            e.printStackTrace();
            return;
      /*      Dialog dialog =new Dialog(mainController.getBackgroundManager().getLoadedBackground(),menuGrid.getScene().getWindow(),DIALOGTYPE.Information);
            dialog.setTitle("Error");
            dialog.setHeaderText("An error occurred while loading game files . Please check your files and try again");
            dialog.showDialog();
            if(dialog.getResult())
                return;*/
        } catch (NullPointerException e) {
            //    System.out.println("How to load problems");
            showErrorDialog("Error", "An error occurred while loading game files . Please check your files and try again or reinstall game");
            e.printStackTrace();    return;
            //  showErrorDialog("Error","Hakuna matata");
            /*  Dialog dialog =new Dialog(mainController.getBackgroundManager().getLoadedBackground(),menuGrid.getScene().getWindow(),DIALOGTYPE.Information);
            dialog.setTitle("Error");
            dialog.setHeaderText("An error occurred while loading game files .  Please check your files and try again");
            dialog.showDialog();
            if(dialog.getResult())
                return;*/
        }
       /* if (configurationManager.getParameter("Key") == null) {
            boolean result;
            String validKey = "";

            do {
                Dialog dialog = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Input);
                dialog.setTitle("Validation required");
                dialog.setHeaderText("Please enter a valid access key, obtained from the developer");
                dialog.showDialog();
                result = dialog.getResult();
            } while (!result);
            try {
                if (DataExchangeManager.validateKey(validKey))
                    configurationManager.saveKey(validKey);
            } catch (IOException e) {
                Dialog dialog = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Information);
                dialog.setTitle("Error");
                dialog.setHeaderText("An error occurred while validating key. Please check your connection");
                dialog.showDialog();
                if (dialog.getResult())
                    return;
            }
        }*/
        Newgame newgameController = loader.getController();
//        newgameController.setConfigurationManager(this.configurationManager);

        newgameController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    public void setOptions() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/options.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();

        } catch (IOException e) {
            showErrorDialog("Error", "An error occurred while loading screen. Please check your files or reinstall game");
            return;
        }

        Options optionsController = loader.getController();

        optionsController.setMainController(mainController);
        optionsController.setConfigurationManager(configurationManager);
        optionsController.loadData();
        mainController.setScreen(pane);
    }

    @FXML
    public void openHighscoreTable() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/highscore.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();

        } catch (IOException e) {
            //   System.out.println("How to exception");
        /*    Dialog dialog =new Dialog(mainController.getBackgroundManager().getLoadedBackground(),menuGrid.getScene().getWindow(),DIALOGTYPE.Information);
            dialog.setTitle("Error");
            dialog.setHeaderText("An error occurred while loading data from server. Please check your connection");
            dialog.showDialog();
            if(dialog.getResult())
                return;*/
      //  e.printStackTrace();
            showErrorDialog("Error", "An error occurred while loading data from server. Please check your connection");
            return;
        } catch (NullPointerException e) {
            // System.out.println("How to exceptionsad");
        /*    Dialog dialog =new Dialog(mainController.getBackgroundManager().getLoadedBackground(),menuGrid.getScene().getWindow(),DIALOGTYPE.Information);
            dialog.setTitle("Error");
            dialog.setHeaderText("An error occurred while loading data from server. Please check your connection");
            dialog.showDialog();
            if(dialog.getResult())
                return;*/
            showErrorDialog("Error", "An error occurred while loading data from server. Please check your connection");
            return;
        }

        Highscore highscoreController = loader.getController();
        System.out.println(configurationManager!=null);
        highscoreController.setConfigurationManager(configurationManager);

        highscoreController.setMainController(mainController);
        try {
            highscoreController.loadData();
        } catch (NullPointerException e) {
            System.out.println("How to do do to do do");
            try {
                showErrorDialog("Error", "An error occurred while loading data from server. Please check your connection");
            } catch (NullPointerException se) {
                System.out.println("How ?????");
            }

            return;
           /* Dialog dialog =new Dialog(mainController.getBackgroundManager().getLoadedBackground(),menuGrid.getScene().getWindow(),DIALOGTYPE.Information);
            dialog.setTitle("Error");
            dialog.setHeaderText("An error occurred while loading data from server. Please check your connection");
            dialog.showDialog();
            if(dialog.getResult())
              return;*/
        }

        mainController.setScreen(pane);
   /*     Stage newWindow=new Stage();
        Background bg= this.mainController.getBackgroundManager().getLoadedBackground();

   *//*     yes.setOnAction(event ->  {
        newWindow.close();
        Save();
    });*//*
        //    cancel.setOnAction(event -> newWindow.close());
        Pane modalWindowPane= new Pane();
        modalWindowPane.setBackground(bg);
        Button okButton = new Button("Ok");
        okButton.setOnAction(event -> event.consume());
        modalWindowPane.getChildren().add(okButton);

//       gridPane.getChildren().addAll(secondLabel, okButton, cancelButton);
        //   root.getChildrenUnmodifiable().get(0);
        Scene secondScene = new Scene(modalWindowPane, 300, 200);
        // New window (Stage)
        modalWindowPane.setBackground(bg);
        newWindow.initStyle(StageStyle.DECORATED);
        newWindow.setOnCloseRequest(windowEvent -> windowEvent.consume());
        newWindow.setTitle("Do you wish to continue ??");
        newWindow.setScene(secondScene);
        newWindow.setResizable(false);
        newWindow.setAlwaysOnTop(true);
        newWindow.toFront();
        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);
        Window primaryStage = modalWindowPane.getScene().getWindow();
        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(primaryStage);
        // Set position of second window, related to primary window.
        *//*newWindow.setX(primaryStage.getX()+500);
        newWindow.setY(primaryStage.getY() + 500);*//*
        newWindow.centerOnScreen();
        newWindow.showAndWait();
        newWindow.close();*/
    }

    @FXML
    public void openCredits() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../resources/credits.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();

        } catch (IOException e) {
            showErrorDialog("Error", "An error occurred while loading screen. Please check your files or reinstall game");
            e.printStackTrace();
        }


        Credits creditsController = loader.getController();
        creditsController.setMainController(mainController);

        mainController.setScreen(pane);
    }

    public void exit() {
        Dialog dialog = new Dialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Saving);
        dialog.setTitle("Exit  from  game");
        dialog.setHeaderText("Are  you  sure ?");
        dialog.showDialog();
        if (dialog.getResult())
            System.exit(1);

    }
}
