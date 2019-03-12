package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class Main extends Application {
    private ConfigurationManager configurationManager = new ConfigurationManager();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root=null;
        try {

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/resources/mainScreen.fxml"));
          //  loader.setLocation(getClass().getResource("/resources/mainScreen.fxml"));
            root = loader.load();
        }catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR,"nie da sie zaladować mainScreen.fxml      "+ e.getMessage(), ButtonType.OK);
            a.setTitle("Błądoł");
            a.showAndWait();
            System.exit(-1);
        }
            primaryStage.setTitle("Train Your Memo!");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            primaryStage.setFullScreen(true);
            primaryStage.show();
            try {
                configurationManager.loadParameters();
            }catch (Exception e) {
                e.printStackTrace();
                Alert a = new Alert(Alert.AlertType.ERROR," config.txt sie wysypal "+ e.getMessage(), ButtonType.OK);
                a.setTitle("Błądoł");
                a.showAndWait();
                System.exit(-1);
            }

            /*  for (int i = 0; i < 20; i++) {
                System.out.println("iteracja "+i +"  "+ BCrypt.hashpw(configurationManager.getParameter("Key"),BCrypt.gensalt()));
                System.out.println("is good"+ BCrypt.checkpw(configurationManager.getParameter("Key"), BCrypt.hashpw(configurationManager.getParameter("Key"),BCrypt.gensalt())));
            }*/

            if (configurationManager.getParameter("Key").equals("000000")) {
         int result=-1;
                do{
                Dialog dialog = new Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Input);

                dialog.setTitle("Please enter a valid access key ");
                dialog.showDialog();
              //  flag=dialog.getResult();
                    result=dialog.getValidationResult();
                if (1==result) {
                    // TODO: 2019-02-28 may be redundant, save key to string and push it further or what ...
                    Dialog success = new Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Information);
                    success.setTitle("Success ! ");
                    success.setHeaderText("Key is valid, remember to do not lose it or give it to someone else! ");
                    success.showDialog();
                    configurationManager.saveKey(dialog.getPassword());
                    configurationManager.saveLogin(dialog.getLogin());

                }else if(-1==result)
                    {
                        Dialog success = new Dialog(new BackgroundManager(this.getClass(), configurationManager.getBackgroundPath()).getLoadedBackground(), primaryStage.getOwner(), DIALOGTYPE.Information);
                        success.setTitle("Failed ! ");
                        success.setHeaderText("Key is not valid, please type your personal acces key properly, or get one from www.mypage.com ");
                        success.showDialog();
                    }
                else
                    {
                        System.exit(1);
                    }
              }while(result!=1);


              //  System.out.println("result " + success.getResult());
                primaryStage.show();

            }


    }


    public static void main(String[] args) {
        try{
        launch(args);
    }catch (Exception e) {
        e.printStackTrace();
        Alert a = new Alert(Alert.AlertType.ERROR," Błąd "+ e.getMessage(), ButtonType.OK);
        a.setTitle("Błąd");
        a.showAndWait();
        System.exit(-1);
    }
    }
}
