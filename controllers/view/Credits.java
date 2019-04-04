package controllers.view;


import controllers.MainController;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Credits {
    @FXML
    private Label infoLabel;
    @FXML
    private Label additionalLabel;
    @FXML
    private Button exitButton;
    private MainController mainController;
    public void initialize()
    {
        // TODO: 2019-03-07 do I initialize sth here ?!
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void Back() {
        mainController.loadMainScreen();
    }
}
