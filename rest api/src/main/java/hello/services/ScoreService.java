package hello.services;

import hello.entities.dataObjects.BestScore;
import hello.entities.dataObjects.ListViewMapped;
import hello.utilities.enums.LEVELS;


import java.sql.Time;

public interface ScoreService {
    boolean addNewScore (Time score, String validationKey, String pack, LEVELS level);
    BestScore bestScoreByKey(String validationKey, LEVELS level);
    Iterable<ListViewMapped> getAllScores( String validationKey,LEVELS level);

}
