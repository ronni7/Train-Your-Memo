package controllers.nonView;

import controllers.nonView.BestScore;

public class BestScoreEntity implements BestScore {
    public BestScoreEntity(String score, String pack) {
        this.score = score;
        this.pack = pack;
    }

    private String score;
    private String pack;
    @Override
    public String getScore() {
        return score;
    }

    @Override
    public String getPack() {
        return pack;
    }
}
