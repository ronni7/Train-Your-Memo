package controllers;

import backgroundHandler.BackgroundManager;
import configurationFileHandler.ConfigurationManager;
import utilities.enums.DIALOGTYPE;
import customDialogs.Dialog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    private final ConfigurationManager configurationManager = new ConfigurationManager();

    @Override
    public void start(Stage primaryStage){
        try {

            GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            double width = graphicsDevice.getDisplayMode().getWidth();
            double height = graphicsDevice.getDisplayMode().getHeight();
            configurationManager.loadParameters();

            if (width < 1250 || height < 750) {
                Dialog dialog = new Dialog(new BackgroundManager(this.getClass(),
                        configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Information);
               dialog.setTitle("Resolution problem");
                dialog.setHeaderText("Your device has too small screen resolution!"+System.lineSeparator() +"  The game cannot run ");
                dialog.showDialog();
                if(dialog.getResult())
                    System.exit(-1);
            }




            if (configurationManager.getParameter("Key").equals("000000")) {
                int result = -1;
                do {
                    Dialog dialog = new Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Input);
                    dialog.setTitle("Please enter a valid access key ");
                    dialog.showDialog();
                    result = dialog.getValidationResult();
                    if (1 == result) {
                        Dialog success = new Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Information);
                        success.setTitle("Success ! ");
                        success.setHeaderText("Key is valid, remember to do not lose it or give it to someone else! ");
                        success.showDialog();
                        configurationManager.saveKey(dialog.getAccessKey());
                        configurationManager.saveLogin(dialog.getLogin());
                        loadMainScreen(primaryStage);
                    } else if (-1 == result) {
                        Dialog success = new Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Information);
                        success.setTitle("Failed ! ");
                        success.setHeaderText("Key is not valid, please type your personal access key properly ");
                        success.showDialog();
                    } else {
                        System.exit(1);
                    }
                } while (result != 1);

                primaryStage.show();
            }
            loadMainScreen(primaryStage);

        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Couldn't load files, please check your files, and rerun game", ButtonType.OK);
            a.setTitle("Error file not found");
            a.showAndWait();
            System.exit(-1);
        }
    }

    private void loadMainScreen(Stage primaryStage) throws java.io.IOException {
        Parent root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/mainScreen.fxml"));
        root = loader.load();
        primaryStage.setTitle("Train Your Memo !");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
