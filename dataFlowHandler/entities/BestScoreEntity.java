package controllers.dataFlowHandler.entities;

import controllers.dataFlowHandler.entityModels.BestScore;

public class BestScoreEntity implements BestScore {
    public BestScoreEntity(String score, String pack) {
        this.score = score;
        this.pack = pack;
    }

    private final String score;
    private final String pack;
    @Override
    public String getScore() {
        return score;
    }

    @Override
    public String getPack() {
        return pack;
    }
}
