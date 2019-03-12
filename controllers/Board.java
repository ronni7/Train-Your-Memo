package controllers;

import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.stage.Window;

import java.util.ArrayList;

public class Board {
    public ArrayList<BoardNode> list = new ArrayList<>();

    private int counted = 0;

    public boolean win() {
        return counted == list.size();
    }

    public int GetIdFromToggle(ToggleButton toggleButton) {
        int match = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getToggle() == toggleButton)
                match = list.get(i).getId();
        }
        return match;
    }

    public void DisableByID(int id) {
        for (BoardNode bn : list
        ) {
            if (id == bn.getId()) {
                bn.getToggle().setVisible(false);
                this.counted++;
                //   System.out.println("counted: "+this.counted);
            }

        }
    }

    public void unselectToggleById(int id) {
        for (BoardNode bn : list
        ) {
            if (id == bn.getId()) {
                bn.getToggle().setSelected(false);
            }

        }
    }

}
