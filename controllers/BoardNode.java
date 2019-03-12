package controllers;

import javafx.scene.control.ToggleButton;

public class BoardNode {
    private ToggleButton toggle;
    private int id;

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
