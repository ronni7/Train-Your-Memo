package controllers.nonView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class Dialog {

    private Pane pane;
    private Background background;
    private Window parent;
    private boolean result;
    private int validationResult;
    private DIALOGTYPE type;
    private Stage newWindow;
    private LinkedList<HBox> boxes;
    private HBox titleHbox;
    private HBox headerHbox;
    private HBox buttonHBox;
    private Label title;
    private Label header;
    private ArrayList<Button> buttons;
    private TextField login;
    private TextField accessKey;
    private HBox websiteHbox;

    public void setHeaderText(String txt) {
        this.header.setText(txt);
    }

    public Dialog(Background background, Window parent, DIALOGTYPE type) {
        this.background = background;
        this.parent = parent;
        this.type = type;
        this.validationResult = -1;
        this.result = false;
        this.newWindow = new Stage();
        this.pane = new Pane();
        pane.setId("dialogPane");
        this.title = new Label("Title");
        this.header = new Label("Header");
        this.buttons = new ArrayList<>();
        this.boxes = new LinkedList<>();
        this.buttonHBox = new HBox();
        this.headerHbox = new HBox();
        this.titleHbox = new HBox();
        this.login = new TextField("login");
        this.accessKey = new TextField("accesskey");

    }

    public int getValidationResult() {
        return validationResult;
    }

    private void adjustElementsSizes() {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        double width = graphicsDevice.getDisplayMode().getWidth();
        double height = graphicsDevice.getDisplayMode().getHeight();

        if (type == DIALOGTYPE.Input) {
            pane.setPrefSize(0.6 * width, 0.6 * height);
              headerHbox.setPrefSize(pane.getPrefWidth(), titleHbox.getPrefHeight() * 2);
            titleHbox.setPrefSize(pane.getPrefWidth(), 0.1 * pane.getPrefHeight());
            buttonHBox.setLayoutY(0.7 * pane.getPrefHeight());
        } else{
            pane.setPrefSize(0.4 * width, 0.3 * height);
            titleHbox.setPrefSize(pane.getPrefWidth(), 0.1 * pane.getPrefHeight());
            headerHbox.setPrefSize(pane.getPrefWidth(), titleHbox.getPrefHeight() * 4);
            buttonHBox.setLayoutY(0.6 * pane.getPrefHeight());
        }

        titleHbox.setLayoutX(0);//may not be useful
        titleHbox.setLayoutY(0);//may not be useful
        title.setAlignment(Pos.CENTER);
        title.setContentDisplay(ContentDisplay.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setTextOverrun(OverrunStyle.ELLIPSIS);
        title.setWrapText(true);
        titleHbox.setAlignment(Pos.CENTER);
        titleHbox.getChildren().add(title);
        boxes.add(titleHbox);
        headerHbox.setLayoutX(0);
        headerHbox.setLayoutY(0.1 * pane.getPrefHeight());
        headerHbox.setAlignment(Pos.CENTER);
        header.setPrefSize(headerHbox.getPrefWidth(), headerHbox.getPrefHeight());
        header.setWrapText(true);
        header.setAlignment(Pos.CENTER);
        header.setContentDisplay(ContentDisplay.CENTER);
        header.setTextAlignment(TextAlignment.CENTER);
        headerHbox.getChildren().add(header);
        boxes.add(headerHbox);
        if (type == DIALOGTYPE.Input) {
            Hyperlink hyperlink = new Hyperlink("www.trainYourMemo.com");
            hyperlink.setBorder(Border.EMPTY);
            hyperlink.setAlignment(Pos.CENTER);
            hyperlink.setOnAction(event -> {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI(hyperlink.getText()));
                    } catch (IOException e) {
                        /// TODO: 2019-04-03 browser no workerino error message
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        /// TODO: 2019-04-03 browser no workerino error message
                    }
                }
            });
            TextFlow textFlow = new TextFlow(hyperlink);
            websiteHbox = new HBox();
            websiteHbox.setPrefSize(0.5 * pane.getPrefWidth(), titleHbox.getPrefHeight());
            websiteHbox.setLayoutY(0.2 * pane.getPrefHeight());
            websiteHbox.setLayoutX(0.25 * pane.getPrefWidth());
            websiteHbox.setAlignment(Pos.CENTER);

            textFlow.setTextAlignment(TextAlignment.CENTER);
            textFlow.setPrefSize(websiteHbox.getPrefWidth(), websiteHbox.getPrefHeight());

            websiteHbox.getChildren().add(textFlow);
            textFlow.setId("websiteHyperlink");
            boxes.add(websiteHbox);
            HBox loginHbox = new HBox();

            HBox.setHgrow(loginHbox, Priority.ALWAYS);
            loginHbox.setPrefSize(pane.getPrefWidth() / 2, headerHbox.getPrefHeight() / 2);
            loginHbox.setLayoutX(0.25 * pane.getPrefWidth());
            loginHbox.setLayoutY(0.4 * pane.getPrefHeight());
            loginHbox.setAlignment(Pos.CENTER);

            login.setPrefSize(loginHbox.getPrefWidth(), loginHbox.getPrefHeight());
            login.setAlignment(Pos.CENTER);

            loginHbox.getChildren().add(login);
            boxes.add(loginHbox);


            HBox accessKeyHbox = new HBox();
            HBox.setHgrow(accessKeyHbox, Priority.ALWAYS);
            accessKeyHbox.setPrefSize(loginHbox.getPrefWidth(), loginHbox.getPrefHeight());
            accessKeyHbox.setLayoutX(0.25 * pane.getPrefWidth());
            accessKeyHbox.setLayoutY(0.55 * pane.getPrefHeight());
            accessKeyHbox.setAlignment(Pos.CENTER);
            accessKey.setPrefSize(accessKeyHbox.getPrefWidth(), accessKeyHbox.getPrefHeight());
            accessKey.setAlignment(Pos.CENTER);
            accessKeyHbox.getChildren().add(accessKey);

            boxes.add(accessKeyHbox);
        }


        buttonHBox.setPrefSize(0.8 * pane.getPrefWidth(), 0.3 * pane.getPrefHeight());
        buttonHBox.setLayoutX(0.1 * pane.getPrefWidth());

        buttonHBox.setPadding(new Insets(0.15 * buttonHBox.getPrefHeight()));
        buttonHBox.setSpacing(0.2 * buttonHBox.getPrefWidth());
        buttonHBox.setAlignment(Pos.CENTER);
        int buttonCount = buttons.size();
        if (buttonCount == 1)
            buttons.get(0).setPrefSize(buttonHBox.getPrefWidth() / 2, buttonHBox.getPrefHeight() / 2);
        else
            for (Button b : buttons)
                b.setPrefSize(buttonHBox.getPrefWidth() / buttonCount, buttonHBox.getPrefHeight() / buttonCount);
        buttonHBox.getChildren().addAll(buttons);
        boxes.add(buttonHBox);
        //  pane.getChildren().addAll(titleHbox, headerHbox, buttonHBox);

        pane.getChildren().addAll(boxes);
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


                header.setText("If you somehow got the game, and do not own an access key, get one from:");

                //header=textFlow;

                login.setOnAction(event ->
                {
                    String text = login.getText();
                    if (isInvalidLoginOrKey(text))
                        login.setText(text);
                });

                accessKey.setOnAction(event ->
                        {
                            String text = accessKey.getText(); //event.getSource().getText(); is redundant
                            if (text.length() > 6) {
                                text = text.substring(0, 6);
                                accessKey.setText(text);
                            }
                            if (isInvalidLoginOrKey(text))
                                // TODO: 2019-02-09 replacing text
                                accessKey.setText(text);

                        }
                );
                Button ok = new Button("Validate");

                ok.setOnAction(event -> {

                    try {
                        result = DataExchangeManager.validateKey(login.getText(), accessKey.getText());
                        if (result)
                            validationResult = 1;
                    } catch (IOException e) {
                        result = false;
                        validationResult = -1;
                    }
                    newWindow.close();
                });
                Button cancel = new Button("Exit");
                //  ok.setText("");
                cancel.setOnAction(event ->
                        {
                            validationResult = -9;
                            newWindow.close();
                        }
                );
                this.buttons.add(ok);
                this.buttons.add(cancel);
                // this.headerHbox.getChildren().removeAll();
                //  headerHbox.getChildren().addAll(login, accessKey);
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

                adjustElementsSizes();
                buildDialogWindow();
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
                cancelButton.setOnAction(event -> {
                    result = false;
                    newWindow.close();
                });
                buttons.add(okButton);
                buttons.add(cancelButton);
                adjustElementsSizes();
                buildDialogWindow();

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
        Window primaryStage = parent;
        pane.setBackground(background);



        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.setOnCloseRequest(windowEvent -> windowEvent.consume());
        newWindow.setWidth(secondScene.getWidth());

        newWindow.setHeight(secondScene.getHeight());
        newWindow.setScene(secondScene);
        newWindow.getScene().getStylesheets().add(this.getClass().getResource("/style/style.css").toExternalForm());
        newWindow.setResizable(false);
        newWindow.setAlwaysOnTop(true);
        newWindow.toFront();
        newWindow.initModality(Modality.WINDOW_MODAL);

        newWindow.initOwner(primaryStage);
        newWindow.showAndWait();
        newWindow.close();
    }

    public String getAccessKey() {
        return accessKey.getText();
    }

    public String getLogin() {
        return login.getText();
    }
}



