package controllers;

public class ListViewEntity {
    private String nickname;
    private String score;
    private String pack;
    private String level;

    public ListViewEntity(String nickname, String score, String pack, String level) {
        this.nickname = nickname;
        this.score = score;
        this.pack = pack;
        this.level = level;
    }

    public String getNickname() {
        return nickname;
    }

    public String getScore() {
        return score;
    }

    public String getPack() {
        return pack;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return nickname + " " + score;
    }
}
