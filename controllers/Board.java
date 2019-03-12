package controllers;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.stage.Window;

import java.util.ArrayList;

public class Board {
    private ArrayList<BoardNode> list = new ArrayList<>();

    public ArrayList<BoardNode> getList() {
        return list;
    }

    private int counted = 0;

    public boolean win() {
        return counted == list.size();
    }

    public int GetIdFromToggle(ToggleButton toggleButton) {
        for (BoardNode boardNode : list)
            if (boardNode.getToggle() == toggleButton)
             return boardNode.getId();
        return -1;
    }

    public void DisableByID(int id) {
        for (BoardNode bn : list)
            if (id == bn.getId()) {
                bn.getToggle().setVisible(false);
                this.counted++;
                //   System.out.println("counted: "+this.counted);
            }
    }

    public void unselectToggleById(int id) {
        for (BoardNode bn : list)
            if (id == bn.getId()) bn.getToggle().setSelected(false);
    }

}
