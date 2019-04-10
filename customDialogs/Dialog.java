package customDialogs;

import utilities.enums.DIALOGTYPE;
import dataFlowHandler.DataExchangeManager;
import javafx.event.Event;
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

    private final Pane pane;
    protected final Background background;
    protected final Window parent;
    private boolean result;
    private int validationResult;
    protected final DIALOGTYPE type;
    private final Stage newWindow;
    private final LinkedList<HBox> boxes;
    private HBox titleHBox;
    private HBox headerHBox;
    private final HBox buttonHBox;
    private final Label title;
    private final Label header;
    private final ArrayList<Button> buttons;
    private final TextField login;
    private final TextField accessKey;

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
        this.headerHBox = new HBox();
        this.titleHBox = new HBox();
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
              headerHBox.setPrefSize(pane.getPrefWidth(), titleHBox.getPrefHeight() * 2);
            titleHBox.setPrefSize(pane.getPrefWidth(), 0.1 * pane.getPrefHeight());
            buttonHBox.setLayoutY(0.7 * pane.getPrefHeight());
        } else{
            pane.setPrefSize(0.4 * width, 0.3 * height);
            titleHBox.setPrefSize(pane.getPrefWidth(), 0.1 * pane.getPrefHeight());
            headerHBox.setPrefSize(pane.getPrefWidth(), titleHBox.getPrefHeight() * 4);
            buttonHBox.setLayoutY(0.6 * pane.getPrefHeight());
        }
        titleHBox.setLayoutX(0);
        titleHBox.setLayoutY(0);
        title.setAlignment(Pos.CENTER);
        title.setContentDisplay(ContentDisplay.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setTextOverrun(OverrunStyle.ELLIPSIS);
        title.setWrapText(true);
        titleHBox.setAlignment(Pos.CENTER);
        titleHBox.getChildren().add(title);
        boxes.add(titleHBox);
        headerHBox.setLayoutX(0);
        headerHBox.setLayoutY(0.1 * pane.getPrefHeight());
        headerHBox.setAlignment(Pos.CENTER);
        header.setPrefSize(headerHBox.getPrefWidth(), headerHBox.getPrefHeight());
        header.setWrapText(true);
        header.setAlignment(Pos.CENTER);
        header.setContentDisplay(ContentDisplay.CENTER);
        header.setTextAlignment(TextAlignment.CENTER);
        headerHBox.getChildren().add(header);
        boxes.add(headerHBox);
        if (type == DIALOGTYPE.Input) {
            Hyperlink hyperlink = new Hyperlink("www.trainYourMemo.com");
            hyperlink.setBorder(Border.EMPTY);
            hyperlink.setAlignment(Pos.CENTER);
            hyperlink.setOnAction(event -> {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI(hyperlink.getText()));
                    } catch (IOException e) {
                        ErrorDialog errorDialog=new ErrorDialog(background,parent, type);
                        errorDialog.showErrorDialog("Error", "An error occurred while opening browser.");

                    } catch (URISyntaxException e) {
                        ErrorDialog errorDialog=new ErrorDialog(background,parent, type);
                        errorDialog.showErrorDialog("Error", "An URL error happened, web page you are trying to access is not available");
                   
                    }
                }
            });
            TextFlow textFlow = new TextFlow(hyperlink);
            HBox websiteHBox = new HBox();
            websiteHBox.setPrefSize(0.5 * pane.getPrefWidth(), titleHBox.getPrefHeight());
            websiteHBox.setLayoutY(0.2 * pane.getPrefHeight());
            websiteHBox.setLayoutX(0.25 * pane.getPrefWidth());
            websiteHBox.setAlignment(Pos.CENTER);
            textFlow.setTextAlignment(TextAlignment.CENTER);
            textFlow.setPrefSize(websiteHBox.getPrefWidth(), websiteHBox.getPrefHeight());
            websiteHBox.getChildren().add(textFlow);
            textFlow.setId("websiteHyperlink");
            boxes.add(websiteHBox);
            HBox loginHBox = new HBox();
            HBox.setHgrow(loginHBox, Priority.ALWAYS);
            loginHBox.setPrefSize(pane.getPrefWidth() / 2, headerHBox.getPrefHeight() / 2);
            loginHBox.setLayoutX(0.25 * pane.getPrefWidth());
            loginHBox.setLayoutY(0.4 * pane.getPrefHeight());
            loginHBox.setAlignment(Pos.CENTER);
            login.setPrefSize(loginHBox.getPrefWidth(), loginHBox.getPrefHeight());
            login.setAlignment(Pos.CENTER);
            loginHBox.getChildren().add(login);
            boxes.add(loginHBox);

            HBox accessKeyHBox = new HBox();
            HBox.setHgrow(accessKeyHBox, Priority.ALWAYS);
            accessKeyHBox.setPrefSize(loginHBox.getPrefWidth(), loginHBox.getPrefHeight());
            accessKeyHBox.setLayoutX(0.25 * pane.getPrefWidth());
            accessKeyHBox.setLayoutY(0.55 * pane.getPrefHeight());
            accessKeyHBox.setAlignment(Pos.CENTER);
            accessKey.setPrefSize(accessKeyHBox.getPrefWidth(), accessKeyHBox.getPrefHeight());
            accessKey.setAlignment(Pos.CENTER);
            accessKeyHBox.getChildren().add(accessKey);
            boxes.add(accessKeyHBox);
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
                login.setOnAction(event ->
                {
                    String text = login.getText();
                    if (isInvalidLoginOrKey(text))
                        login.setText(text);
                });

                accessKey.setOnAction(event ->
                        {
                            String text = accessKey.getText();
                            if (text.length() > 6) {
                                text = text.substring(0, 6);
                                accessKey.setText(text);
                            }
                            if (isInvalidLoginOrKey(text))

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
                cancel.setOnAction(event ->
                        {
                            validationResult = -9;
                            newWindow.close();
                        }
                );
                this.buttons.add(ok);
                this.buttons.add(cancel);

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
        pane.setBackground(background);
        newWindow.initStyle(StageStyle.UNDECORATED);
        newWindow.setOnCloseRequest(Event::consume);
        newWindow.setWidth(secondScene.getWidth());
        newWindow.setHeight(secondScene.getHeight());
        newWindow.setScene(secondScene);
        newWindow.getScene().getStylesheets().add(this.getClass().getResource("/resources/style/style.css").toExternalForm());
        newWindow.setResizable(false);
        newWindow.setAlwaysOnTop(true);
        newWindow.toFront();
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(parent);
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



