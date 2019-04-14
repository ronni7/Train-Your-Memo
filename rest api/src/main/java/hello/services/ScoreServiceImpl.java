package hello.services;

import hello.entities.Scores;
import hello.entities.User;
import hello.entities.dataObjects.BestScore;
import hello.entities.dataObjects.ListViewMapped;
import hello.repositories.ScoresRepository;
import hello.repositories.UserRepository;
import hello.utilities.enums.LEVELS;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.sql.Time;
@Component
public class ScoreServiceImpl implements ScoreService {

    private UserRepository userRepository;
    private ScoresRepository scoresRepository;

    public ScoreServiceImpl(UserRepository userRepository, ScoresRepository scoresRepository) {
        this.userRepository = userRepository;
        this.scoresRepository = scoresRepository;
    }

    @Override
    public boolean addNewScore(Time score, String validationKey, String pack, LEVELS level) {
        User user = getUserByValidationKey(validationKey);
        if (user != null) {
            Scores s = new Scores(user.getId(), score, pack, String.valueOf(level));
            scoresRepository.save(s);
            return true;
        }
        return false;
    }

    @Override
    public BestScore bestScoreByKey(String validationKey, LEVELS level) {
        User user = getUserByValidationKey(validationKey);
        if (user != null)
            return scoresRepository.findScoresById(user.getId(), String.valueOf(level));
        return null;
    }

    @Override
    public Iterable<ListViewMapped> getAllScores(String validationKey, LEVELS level) {
        User user = getUserByValidationKey(validationKey);
        if (user != null)
            return scoresRepository.findAllByLevel(String.valueOf(level));
        return null;
    }
    private User getUserByValidationKey(String validationKey) {
        for (User user : userRepository.findAll())
            if (BCrypt.checkpw(validationKey, user.getValidationKey())) return user;
        return null;
    }
}
