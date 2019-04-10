package controllers;

import controllers.utilities.enums.DIALOGTYPE;
import customDialogs.Dialog;
import customDialogs.ErrorDialog;
import controllers.view.Credits;
import controllers.view.Highscore;
import controllers.view.Newgame;
import controllers.view.Options;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;


public class Menu {
    @FXML
    public GridPane menuGrid;
    private controllers.MainController mainController;
    private controllers.ConfigurationManager configurationManager;



    public void setConfigurationManager(controllers.ConfigurationManager configurationManager) {

        this.configurationManager = configurationManager;
        try {
            configurationManager.loadParameters();
        } catch (IOException e) {
            ErrorDialog errorDialog=new ErrorDialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Information);

           errorDialog.showErrorDialog("Error", "An error occurred while loading game files . Please check your files and try again or reinstall game");

        }
    }

    public void setMainController(controllers.MainController mainController) {

        this.mainController = mainController;
    }


    @FXML
    public void newGame() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/resources/newgame.fxml"));
        Pane pane = null;
        try {

            pane = loader.load();

        } catch (IOException e) {

            ErrorDialog errorDialog=new ErrorDialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Information);

            errorDialog.showErrorDialog("Error", "An error occurred while loading game files . Please check your files and try again or reinstall game");
            return;

        } catch (NullPointerException e) {
            ErrorDialog errorDialog=new ErrorDialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Information);

            errorDialog.showErrorDialog("Error", "An error occurred while loading game files . Please check your files and try again or reinstall game");   return;

        }

        Newgame newgameController = loader.getController();


        newgameController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    public void setOptions() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/resources/options.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();

        } catch (IOException e) {
            ErrorDialog errorDialog=new ErrorDialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Information);

            errorDialog.showErrorDialog("Error", "An error occurred while loading game files . Please check your files and try again or reinstall game");
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
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/resources/highscore.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();

        } catch (IOException e) {
            ErrorDialog errorDialog=new ErrorDialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Information);


            errorDialog.showErrorDialog("Error", "An error occurred while loading data from server. Please check your connection");
            return;
        } catch (NullPointerException e) {
            ErrorDialog errorDialog=new ErrorDialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Information);
            errorDialog. showErrorDialog("Error", "An error occurred while loading data from server. Please check your connection");
            return;
        }

        Highscore highscoreController = loader.getController();

        highscoreController.setMainController(mainController);
        try {
            highscoreController.loadData();
        } catch (NullPointerException e) {
            ErrorDialog errorDialog=new ErrorDialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Information);
                errorDialog.showErrorDialog("Error", "An error occurred while loading data from server. Please check your connection");
            return;

        }

        mainController.setScreen(pane);
    }

    @FXML
    public void openCredits() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/resources/credits.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();

        } catch (IOException e) {
            ErrorDialog errorDialog=new ErrorDialog(mainController.getBackgroundManager().getLoadedBackground(), menuGrid.getScene().getWindow(), DIALOGTYPE.Information);
           errorDialog.showErrorDialog("Error", "An error occurred while loading screen. Please check your files or reinstall game");

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
