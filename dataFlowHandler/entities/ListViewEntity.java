package controllers.dataFlowHandler.entities;

import controllers.dataFlowHandler.entityModels.ListViewMapped;

public class ListViewEntity implements ListViewMapped {
    private final String nickname;
    private final String score;
    private final String pack;
    private final String level;

    public ListViewEntity(String nickname, String score, String pack, String level) {
        this.nickname = nickname;
        this.score = score;
        this.pack = pack;
        this.level = level;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public String getScore() {
        return score;
    }

    @Override
    public String getPack() {
        return pack;
    }

    @Override
    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return nickname + " " + score;
    }
}
