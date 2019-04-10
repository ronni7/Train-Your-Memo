package customDialogs;

import utilities.enums.DIALOGTYPE;
import javafx.scene.layout.Background;
import javafx.stage.Window;

public class ErrorDialog extends Dialog {
    public ErrorDialog(Background background, Window parent, DIALOGTYPE type) {
        super(background, parent, type);
    }

    public void showErrorDialog(String errorTitle, String errorHeader) {
        Dialog dialog = new Dialog(background, parent, type);
        dialog.setTitle(errorTitle);
        dialog.setHeaderText(errorHeader);
        dialog.showDialog();
    }
}
