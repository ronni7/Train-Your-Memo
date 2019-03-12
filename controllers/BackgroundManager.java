package controllers;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class BackgroundManager {
    private Background background;
    public BackgroundManager(Class callingClass,String path) throws NullPointerException{

        if(callingClass != null && !path.isEmpty()) {
                Image image = new Image(callingClass.getResourceAsStream(path));
                BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
                BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                background= new Background(backgroundImage);
        }
    }

public    Background getLoadedBackground()
    {
        if(background!=null)
        return this.background;
        else return null;
    }
}
