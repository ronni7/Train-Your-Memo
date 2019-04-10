package controllers.gameBoard;

import javafx.scene.control.ToggleButton;

public class BoardNode {
    private final ToggleButton toggle;
    private final int id;

    public BoardNode(ToggleButton toggle,  int id) {
        this.toggle = toggle;
        this.id = id;
    }


    public ToggleButton getToggle() {
        return toggle;
    }

    public int getId() {
        return id;
    }

}
