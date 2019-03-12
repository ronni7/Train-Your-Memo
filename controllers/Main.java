package controllers;

import javafx.application.Application;
import javafx.application.Platform;
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
            Parent root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/mainScreen.fxml"));
            root = loader.load();
            GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            double width = graphicsDevice.getDisplayMode().getWidth();
            double height = graphicsDevice.getDisplayMode().getHeight();
            primaryStage.setTitle("Train Your Memo !");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            primaryStage.setFullScreen(true);

            configurationManager.loadParameters();
          /*  for (int i = 0; i < 20; i++) {
                System.out.println("iteracja "+i +"  "+ BCrypt.hashpw(configurationManager.getParameter("Key"),BCrypt.gensalt()));
                System.out.println("is good"+ BCrypt.checkpw(configurationManager.getParameter("Key"), BCrypt.hashpw(configurationManager.getParameter("Key"),BCrypt.gensalt())));
            }*/
            if (width < 1250 || height < 750) {
                Dialog dialog = new Dialog(new BackgroundManager(this.getClass(),
                        configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Information);
               dialog.setTitle("Resolution problem");
                dialog.setHeaderText("Your device has too small screen resolution!"+System.lineSeparator() +"  The game cannot run ");
                dialog.showDialog();
                if(dialog.getResult())
                    System.exit(-1);
            }

            primaryStage.show();


            if (configurationManager.getParameter("Key").equals("000000")) {
                int result = -1;
                do {
                    Dialog dialog = new Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Input);
                    dialog.setTitle("Please enter a valid access key ");
                    dialog.showDialog();
                    result = dialog.getValidationResult();
                    if (1 == result) {
                        // TODO: 2019-02-28 may be redundant, save key to string and push it further or what ...
                        Dialog success = new Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Information);
                        success.setTitle("Success ! ");
                        success.setHeaderText("Key is valid, remember to do not lose it or give it to someone else! ");
                        success.showDialog();
                        configurationManager.saveKey(dialog.getPassword());
                        configurationManager.saveLogin(dialog.getLogin());

                    } else if (-1 == result) {
                        Dialog success = new Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Information);
                        success.setTitle("Failed ! ");
                        success.setHeaderText("Key is not valid, please type your personal acces key properly, or get one from www.mypage.com ");
                        success.showDialog();
                    } else {
                        System.exit(1);
                    }
                } while (result != 1);

                primaryStage.show();
            }
        } catch (Exception e) {
           // e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR, "Hello, is it me you lookin for ?", ButtonType.OK);
            a.setTitle("Ritchie");
            a.showAndWait();
            System.exit(-1);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
