package hello.controllers;


import hello.entities.dataObjects.BestScore;
import hello.entities.dataObjects.ListViewMapped;
import hello.entities.Scores;
import hello.entities.User;
import hello.utilities.enums.LEVELS;
import hello.repositories.ScoresRepository;
import hello.repositories.UserRepository;
import hello.utilities.KeyGenerator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;


@Controller
@RequestMapping(path = "/demo")

public class MainController {
    private final UserRepository userRepository;
    private final ScoresRepository scoresRepository;

    public MainController(UserRepository userRepository, ScoresRepository scoresRepository) {
        this.userRepository = userRepository;
        this.scoresRepository = scoresRepository;
    }

    @GetMapping(path = "/hello")
    public @ResponseBody
    String hello() {
        return BCrypt.hashpw("Password", BCrypt.gensalt());
    }

    @RequestMapping(path = "/add") // Map ONLY GET Requests
    public @ResponseBody
    boolean addNewScore
            (@RequestParam Time score, @RequestParam String validationKey, @RequestParam String pack, @RequestParam LEVELS level) {

        User user = getUserByValidationKey(validationKey);
        if (user != null) {
            Scores s = new Scores(user.getId(), score, pack, String.valueOf(level));
            scoresRepository.save(s);
            return true;
        }
            return false;

    }


    @GetMapping(path = "/bestScoreByLevel")
    public @ResponseBody
    BestScore bestScoreByKey(@RequestParam String validationKey, @RequestParam LEVELS level) {

        User user = getUserByValidationKey(validationKey);
        if (user != null)
              return scoresRepository.findScoresById(user.getId(), String.valueOf(level));
        return null;
    }


    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<ListViewMapped> getAllScores(@RequestParam String validationKey, @RequestParam LEVELS level) {

        User user = getUserByValidationKey(validationKey);
        if (user != null)
            return scoresRepository.findAllByLevel(String.valueOf(level));
         return null;

    }

    @RequestMapping(path = "/validateKey")
    public @ResponseBody
    boolean checkValidationKey(@RequestParam String validKey) {

        User user = getUserByValidationKey(validKey);
        if (user != null && !user.getActivated()) {
            user.setActivated(true);
            userRepository.save(user);
            return true;
        }
            return false;

    }

    @PostMapping(path = "/register")
    public @ResponseBody
    String registerNewUser(@RequestParam String name, @RequestParam String login, @RequestParam String nickname) {


        String generatedKey;
        KeyGenerator keyGenerator = new KeyGenerator();
        generatedKey = keyGenerator.generate(6);
        String encrypted = BCrypt.hashpw(generatedKey, BCrypt.gensalt());
        User u = new User(name, login, nickname, encrypted, false);
        userRepository.save(u);
        return generatedKey;
    }

    private User getUserByValidationKey(String validationKey) {
        for (User user : userRepository.findAll())
            if (BCrypt.checkpw(validationKey, user.getValidationKey())) return user;
        return null;
    }

}