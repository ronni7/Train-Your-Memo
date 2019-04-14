package hello.controllers;


import hello.entities.dataObjects.BestScore;
import hello.entities.dataObjects.ListViewMapped;
import hello.entities.Scores;
import hello.entities.User;
import hello.services.ScoreServiceImpl;
import hello.services.UserService;
import hello.services.UserServiceImpl;
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

    private final UserServiceImpl userService;
    private final ScoreServiceImpl scoreService;

    public MainController(UserServiceImpl userService, ScoreServiceImpl scoreService) {
        this.userService = userService;
        this.scoreService = scoreService;
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


   return scoreService.addNewScore(score,validationKey,pack,level);
    }


    @GetMapping(path = "/bestScoreByLevel")
    public @ResponseBody
    BestScore bestScoreByKey(@RequestParam String validationKey, @RequestParam LEVELS level) {

      return scoreService.bestScoreByKey(validationKey,level);
    }


    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<ListViewMapped> getAllScores(@RequestParam String validationKey, @RequestParam LEVELS level) {

     return scoreService.getAllScores(validationKey,level);

    }

    @RequestMapping(path = "/validateKey")
    public @ResponseBody
    boolean checkValidationKey(@RequestParam String validKey,@RequestParam String login) {

    return userService.checkValidationKey(validKey,login);
    }

    @PostMapping(path = "/register")
    public @ResponseBody
    String registerNewUser(@RequestParam String name, @RequestParam String login, @RequestParam String nickname) {



       return userService.registerNewUser(name,login,nickname);
    }

}