package controllers.nonView;

import javafx.scene.control.ToggleButton;

import java.util.ArrayList;

public class Board {
    private int boardHeight;
    private int boardWidth;
    private int size;
    private int[] matchedId;
    private ToggleButton[] buttonsMatched;
    private int counted;

    public Board(int[] matchedId, ToggleButton[] buttonsMatched) {
        this.matchedId = matchedId;
        this.buttonsMatched = buttonsMatched;
        this.counted = 0;
    }


    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    private ArrayList<BoardNode> list = new ArrayList<>();

    public ArrayList<BoardNode> getList() {
        return list;
    }



    public boolean win() {
        return counted == list.size();
    }

    public int GetIdFromToggle(ToggleButton toggleButton) {
        for (BoardNode boardNode : list)
            if (boardNode.getToggle() == toggleButton)
             return boardNode.getId();
        return -1;
    }

    public int[] getMatchedId() {
        return matchedId;
    }

    public ToggleButton[] getButtonsMatched() {
        return buttonsMatched;
    }

    public void DisableByID(int id) {
        for (BoardNode bn : list)
            if (id == bn.getId()) {
                bn.getToggle().setVisible(false);
                counted++;
             }
    }

    public void unselectToggleById(int id) {
        for (BoardNode bn : list)
            if (id == bn.getId()) bn.getToggle().setSelected(false);
    }

    public void calculateBoardHeight() {
        boardHeight=getSize() / getBoardWidth();
    }
    public void clearTables() {
          for (int i = 0; i < 2; i++) {
              matchedId[i] = 0;
            buttonsMatched[i] = null;
         }

    }
}
