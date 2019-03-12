package controllers;


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
       // Font font=new FontManager(50).getFont();
    //  infoLabel.setFont(font);
    //    additionalLabel.setFont(font);
     //   exitButton.setFont(font);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public void Back() {
        mainController.loadMainScreen();
    }
}
