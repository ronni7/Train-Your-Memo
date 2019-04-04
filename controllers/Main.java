package controllers;

import controllers.nonView.DIALOGTYPE;
import controllers.nonView.Dialog;
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
    private ConfigurationManager configurationManager = new ConfigurationManager();

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            double width = graphicsDevice.getDisplayMode().getWidth();
            double height = graphicsDevice.getDisplayMode().getHeight();
            configurationManager.loadParameters();
          /*  for (int i = 0; i < 20; i++) {
                System.out.println("iteracja "+i +"  "+ BCrypt.hashpw(configurationManager.getParameter("Key"),BCrypt.gensalt()));
                System.out.println("is good"+ BCrypt.checkpw(configurationManager.getParameter("Key"), BCrypt.hashpw(configurationManager.getParameter("Key"),BCrypt.gensalt())));
            }*/
            if (width < 1250 || height < 750) {
                controllers.nonView.Dialog dialog = new controllers.nonView.Dialog(new BackgroundManager(this.getClass(),
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
                    controllers.nonView.Dialog dialog = new controllers.nonView.Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Input);
                    dialog.setTitle("Please enter a valid access key ");
                    dialog.showDialog();
                    result = dialog.getValidationResult();
                    if (1 == result) {
                        // TODO: 2019-02-28 may be redundant, save key to string and push it further or what ...
                        controllers.nonView.Dialog success = new controllers.nonView.Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Information);
                        success.setTitle("Success ! ");
                        success.setHeaderText("Key is valid, remember to do not lose it or give it to someone else! ");
                        success.showDialog();
                        configurationManager.saveKey(dialog.getAccessKey());
                        configurationManager.saveLogin(dialog.getLogin());
                        Parent root;
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/resources/mainScreen.fxml"));
                        root = loader.load();
                        primaryStage.setTitle("Train Your Memo !");
                        primaryStage.setScene(new Scene(root, 600, 400));
                        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                        primaryStage.setFullScreen(true);
                        primaryStage.show();
                    } else if (-1 == result) {
                        controllers.nonView.Dialog success = new Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Information);
                        success.setTitle("Failed ! ");
                        success.setHeaderText("Key is not valid, please type your personal acces key properly, or get one from www.mypage.com ");
                        success.showDialog();
                    } else {
                        System.exit(1);
                    }
                } while (result != 1);

                primaryStage.show();
            }
                    Parent root;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/resources/mainScreen.fxml"));
                    root = loader.load();
                    primaryStage.setTitle("Train Your Memo !");
                    primaryStage.setScene(new Scene(root, 600, 400));
                    primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    primaryStage.setFullScreen(true);
                    primaryStage.show();

        } catch (Exception e) {
           // e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Hello, is it me you lookin for ?", ButtonType.OK);
            a.setTitle("Ritchie");
            a.showAndWait();
            System.exit(-1);
        }
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
