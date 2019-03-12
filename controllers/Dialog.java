package controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.awt.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Dialog {

    public Pane pane;
    private Background background;
    private Window parent;
    private boolean result;
    private int validationResult;
    private DIALOGTYPE type;
    private Stage newWindow;
    private HBox titleHbox;
    private HBox headerHbox;
    private HBox buttonHBox;
    private Label title;
    private Label header;
    private ArrayList<Button> buttons;
    private double height;
   private TextField login = new TextField("login");
    private   TextField password = new TextField("accesskey");
    private double width;
    private String salt;
    public void setHeaderText(String txt) {
        this.header.setText(txt);
    }

    public Dialog(Background background, Window parent, DIALOGTYPE type) {
        this.background = background;
        this.parent = parent;
        this.type = type;
        this.validationResult=-1;
        this.result = false;
        this.newWindow = new Stage();
        this.pane = new Pane();
        this.title = new Label("Title");
        this.header = new Label("Header");
        this.buttons = new ArrayList<>();
        this.buttonHBox = new HBox();
        this.headerHbox = new HBox();
        this.titleHbox = new HBox();
    }

    public int getValidationResult() {
        return validationResult;
    }

    private void adjustElementsSizes() {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = graphicsDevice.getDisplayMode().getWidth();
        height = graphicsDevice.getDisplayMode().getHeight();
        //  System.out.println("width: " + width + "  height " + height);
        this.pane.setPrefSize(0.4 * width, 0.3 * height);
        //   System.out.println("pref pane width: " + pane.getPrefWidth() + "  height " + pane.getPrefHeight());
        //   pane.setLayoutX(0.3 * width);
        //   pane.setLayoutY(0.4 * height);
        //  System.out.println("positionx : " + pane.getLayoutX() + "  y:  " + pane.getLayoutY());
        titleHbox.setPrefSize(pane.getPrefWidth(), 0.2 * pane.getPrefHeight());
        titleHbox.setLayoutX(0);//may not be useful
        titleHbox.setLayoutY(0);//may not be useful
        title.setAlignment(Pos.CENTER);
        title.setContentDisplay(ContentDisplay.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setTextOverrun(OverrunStyle.ELLIPSIS);
        titleHbox.setAlignment(Pos.CENTER);
        titleHbox.getChildren().add(title);

        headerHbox.setPrefSize(pane.getPrefWidth(), titleHbox.getPrefHeight() * 2);
        headerHbox.setLayoutY(0.2 * pane.getPrefHeight());
        headerHbox.setLayoutX(0);
        headerHbox.setAlignment(Pos.CENTER);
        header.setPrefSize(headerHbox.getPrefWidth(), headerHbox.getPrefHeight());
        header.setWrapText(true);
        header.setAlignment(Pos.CENTER);
        header.setContentDisplay(ContentDisplay.CENTER);
        header.setTextAlignment(TextAlignment.CENTER);
        headerHbox.getChildren().add(header);

        buttonHBox.setPrefSize(0.8 * pane.getPrefWidth(), 0.3 * pane.getPrefHeight());
        buttonHBox.setLayoutX(0.1 * pane.getPrefWidth());
        buttonHBox.setLayoutY(0.6 * pane.getPrefHeight());
        buttonHBox.setPadding(new Insets(0.15 * buttonHBox.getPrefHeight()));
        buttonHBox.setSpacing(0.2 * buttonHBox.getPrefWidth());
        int buttonCount = buttons.size();
        for (Button b : buttons) {
            b.setPrefSize(buttonHBox.getPrefWidth() / buttonCount, buttonHBox.getPrefHeight() / buttonCount);
        }
        buttonHBox.getChildren().addAll(buttons);
        pane.getChildren().addAll(titleHbox, headerHbox, buttonHBox);
    }

    public boolean getResult() {
        return result;
    }

    public void setTitle(String text) {
        this.title.setText(text);
    }

    public void showDialog() {
        switch (this.type) {

            case Input: {
                login.setOnAction(event ->
                {
                    String text= login.getText();
                    if(isInvalidLoginOrKey(text))
                        login.setText(text);
                });

                password.setOnAction(event ->
                        {
                            String text = password.getText(); //event.getSource().getText(); is redundant
                            if (text.length() > 6)
                                text = text.substring(0, 6);
                            if (isInvalidLoginOrKey(text))
                                // TODO: 2019-02-09 replacing text
                                password.setText(text);

                        }
                );
                Button ok = new Button("Validate");
                //ok.setText("Validate");
                ok.setOnAction(event -> {
                    //  System.out.println(password.getText().trim());
                    try {
                   //     System.out.println(BCrypt.hashpw(password.getText(),salt));
// save it to file to make things work
                        result = DataExchangeManager.validateKey(login.getText(),password.getText());
                        if(result)
                           validationResult=1;
                    } catch (IOException e) {
                        result = false;
                        validationResult=-1;
                    }
                    newWindow.close();
                });
                Button cancel = new Button("Exit");
                //  ok.setText("");
                cancel.setOnAction(event ->
                        {
validationResult=-9;
                              newWindow.close();
                        }
                );
                this.buttons.add(ok);
                this.buttons.add(cancel);
                this.headerHbox.getChildren().removeAll();
                headerHbox.getChildren().addAll(login,password);
                adjustElementsSizes();
                buildDialogWindow();
                break;
            }
            case Information: {
                Button okButton = new Button("Ok");
                okButton.setOnAction(event -> {
                    newWindow.close();
                    result = true;

                });
                this.buttons.add(okButton);

                //pane.getChildren().addAll(this.header, okButton);

                // New window (Stage)
                adjustElementsSizes();
                buildDialogWindow();

                // Set position of second window, related to primary window.
        /*newWindow.setX(primaryStage.getX()+500);
        newWindow.setY(primaryStage.getY() + 500);*/


                break;

            }
            case Saving: {
                Button okButton = new Button("Yes");
                result = false;

                okButton.setOnAction(event -> {
                    newWindow.close();
                    result = true;

                });
                Button cancelButton = new Button("Cancel");
                cancelButton.setOnAction(event -> newWindow.close());
                buttons.add(okButton);
                buttons.add(cancelButton);

                //   GridPane pane = new GridPane();

                //     pane.add(this.header, 0, 1);
                //       pane.add(okButton, 0, 2);
                //      pane.add(cancelButton, 1, 2);
                //       gridPane.getChildren().addAll(secondLabel, okButton, cancelButton);
                //   root.getChildrenUnmodifiable().get(0);

                //  this.pane.getChildren().add(pane);
                adjustElementsSizes();
                buildDialogWindow();

                // New window (Stage)

                break;
            }
            default: {
                break;
            }

        }

    }

    private boolean isInvalidLoginOrKey(String text) {
        return text.matches(Pattern.compile(("[\\p{L}\\s]+")).pattern());
    }

    private void buildDialogWindow() {
        Scene secondScene = new Scene(pane, this.pane.getPrefWidth(), this.pane.getPrefHeight());
        //
        pane.setBackground(background);


        //  System.out.println("stage width: " + secondScene.getWidth() + "stage  height " + secondScene.getHeight());

        newWindow.initStyle(StageStyle.DECORATED);
        newWindow.setOnCloseRequest(windowEvent -> windowEvent.consume());
        //System.out.println(this.getClass().getResource("/fonts/textstyle.css").toExternalForm()));
        newWindow.setWidth(secondScene.getWidth());
        newWindow.setHeight(secondScene.getHeight());
        newWindow.setScene(secondScene);
        newWindow.getScene().getStylesheets().add(this.getClass().getResource("/fonts/textstyle.css").toExternalForm());
        newWindow.setResizable(false);
        newWindow.setAlwaysOnTop(true);
        newWindow.toFront();

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);
        Window primaryStage = parent;
        //music.getScene().getWindow()

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(primaryStage);

        // Set position of second window, related to primary window.
        /*newWindow.setX(primaryStage.getX()+500);
        newWindow.setY(primaryStage.getY() + 500);*/

        //  newWindow.centerOnScreen();
        newWindow.showAndWait();
        newWindow.close();
    }

    public String getPassword() {
        return password.getText();
    }

    public String getLogin() {
        return login.getText();
    }
}



