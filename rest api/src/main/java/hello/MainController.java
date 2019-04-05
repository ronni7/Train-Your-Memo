package hello;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;


@Controller    // This means that this class is a Controller
@RequestMapping(path = "/demo") // This means URL's start with /demo (after Application path)

public class MainController {
    // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data

    private final UserRepository userRepository;
    private final ScoresRepository scoresRepository;

    @Autowired
    public MainController(UserRepository userRepository, ScoresRepository scoresRepository) {
        this.userRepository = userRepository;
        this.scoresRepository = scoresRepository;
    }


    @GetMapping(path = "/hello")
    public @ResponseBody
    String hello() {
        String hashed = BCrypt.hashpw("Password", BCrypt.gensalt());
        System.out.println("Hello hashed World: ");
        System.out.println("Is the same     " + BCrypt.checkpw("Password", hashed));
        return hashed;

        //  return "hello world";
    }

 /*   @GetMapping(path = "/playerByKey")
    public @ResponseBody //maybe activated also  here
    User findPlayerByKey(@RequestParam String login, @RequestParam String validationKey) {
        User u=userRepository.findByName(login);
        if(u!=null)
            if( BCrypt.checkpw(validationKey,  u.getValidationKey()))
                return u;
        return  null;


    }*/

    @GetMapping(path = "/add") // Map ONLY GET Requests
    public @ResponseBody
    boolean addNewScore
            (@RequestParam Time score, @RequestParam String validationKey, @RequestParam String pack, @RequestParam LEVELS level) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        //  try {
        //  System.out.println("score = [" + score + "], login = [" + login + "], validationKey = [" + validationKey + "], pack = [" + pack + "], level = [" + level + "]");
   /*     User u = userRepository.findByLogin(login);
        System.out.println("u = " + u);

        User user = getUserByValidationKey(validationKey);
        System.out.println("user = " + user);*/
        User user = getUserByValidationKey(validationKey);
        if (user != null) {
            Scores s = new Scores(user.getId(), score, pack, String.valueOf(level));
            scoresRepository.save(s);
            return true;
        }
      /*  if (u != null) {

            if (BCrypt.checkpw(validationKey, u.getValidationKey())) {
         //       System.out.println(u);
                Scores s = new Scores(u.getId(), score, pack, String.valueOf(level));
                scoresRepository.save(s);
                //    System.out.println(s);
                return true;
            }
        }*/
        /*} catch (NullPointerException e) {
            return false;
        }*/
        return false;

    }


    @GetMapping(path = "/bestScoreByLevel")
    public @ResponseBody
    BestScore bestScoreByKey(@RequestParam String validationKey, @RequestParam LEVELS level) {
        //  User u = this.findPlayerByKey(login,validationKey);
        //   System.out.println(scoresRepository.findScoresById(u.getId(), String.valueOf(level)));
/*if(u!=null && u.getActivated())
        return scoresRepository.findScoresById(u.getId(), String.valueOf(level));*/
    /*    User u = userRepository.findByLogin(login);
        System.out.println("u = " + u);

        User user = getUserByValidationKey(validationKey);
        System.out.println("user = " + user);*/
        User user = getUserByValidationKey(validationKey);
        if (user != null)
       /* if (u != null)
            if (BCrypt.checkpw(validationKey, u.getValidationKey()))
                return scoresRepository.findScoresById(u.getId(), String.valueOf(level));*/
            return scoresRepository.findScoresById(user.getId(), String.valueOf(level));

        return null;
    }


    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<ListViewMapped> getAllScores(@RequestParam String validationKey, @RequestParam LEVELS level) {

 /*      // Byte[] array=BoxByteArray(validationKey.getBytes());
        for (Byte aByte : array) {
            System.out.print(aByte);
        }
        System.out.println();*/
        //     User u = findPlayerByKey(login,validationKey);
      /*  System.out.println("validationKey = " + validationKey);
        User u = userRepository.findByLogin(login);
        System.out.println("u = " + u);*/

        User user = getUserByValidationKey(validationKey);
        if (user != null)
            return scoresRepository.findAllByLevel(String.valueOf(level));
        // System.out.println("user = " + user);
        // User u2= userRepository.findByValidationKey();
        //System.out.println("u2 = " + u2);
        /*if (u != null)
            if (BCrypt.checkpw(validationKey, u.getValidationKey()))
                return scoresRepository.findAllByLevel(String.valueOf(level));
*/
        return null;

    }

   /* private Byte[] BoxByteArray(byte[] bytes) {
        Byte[] array=new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            array[i] = bytes[i];

        }
        return array;
    }*/

    @GetMapping(path = "/validateKey")
    public @ResponseBody
    boolean checkValidationKey(@RequestParam String validKey) {

        User user = getUserByValidationKey(validKey);
        if (user != null && !user.getActivated()) {
            user.setActivated(true);
            userRepository.save(user);
            return true;
        }
        /*   User u = userRepository.findByLogin(login);
        System.out.println("u = " + u);
        User u2= userRepository.findByValidationKey(BCrypt.hashpw(validKey,BCrypt.gensalt()));
        System.out.println("u2 = " + u2);
        if (u != null)
            if (BCrypt.checkpw(validKey, u.getValidationKey())) {
                u.setActivated(true);
                userRepository.save(u);
                return true;
            }*/

        return false;

    }

    @PostMapping(path = "/register")
    public @ResponseBody
    String registerNewUser(@RequestParam String name, @RequestParam String login, @RequestParam String nickname) {


        String generatedKey;
        KeyGenerator keyGenerator = new KeyGenerator();
        generatedKey = keyGenerator.generate(6);
        //   System.out.println("generatedKey = " + generatedKey);
        String encrypted = BCrypt.hashpw(generatedKey, BCrypt.gensalt());
        //   System.out.println("BCrypt.hashpw(generatedKey,BCrypt.gensalt()) = " + encrypted);
        //    System.out.println("BCrypt.checkpw(generatedKey,) = " + BCrypt.checkpw(generatedKey,encrypted));
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