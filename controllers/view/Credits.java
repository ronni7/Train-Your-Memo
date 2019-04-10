package controllers.view;


import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class Credits {
    @FXML
    private Label infoLabel;
    @FXML
    private Label additionalLabel;
    @FXML
    private Button exitButton;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void Back() {
        mainController.loadMainScreen();
    }
}
