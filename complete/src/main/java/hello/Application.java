package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Time;
import java.text.SimpleDateFormat;


@SpringBootApplication
public class Application {
   // private static final Logger log =  LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
 //   @Bean
   /* public CommandLineRunner demo(UserRepository repository,ScoresRepository scoresRepository) {
        return (args) -> {
            // save a couple of Users
            repository.save(new User("Jack","Lebowski","Jackie","AX7KXD"));
            repository.save(new User("Mia","Price","Miami","DX4XS1"));
            repository.save(new User("Chloe","McCully","Indigo","FH67DH"));
            repository.save(new User("Steven","Kowalsky","Destroyer","78SDLO"));
            repository.save(new User("John","Stone","Wrapper","HSD72K"));
            scoresRepository.save(new Scores(new SimpleDateFormat("mm:ss").parse("00:37"),"Hackathon",LEVELS.Average,1,repository.findByValidationKey("AX7KXD")));
            scoresRepository.save(new Scores(new SimpleDateFormat("mm:ss").parse("00:51"),"Hackathon",LEVELS.Average,1,repository.findByValidationKey("AX7KXD")));
            scoresRepository.save(new Scores(new SimpleDateFormat("mm:ss").parse("00:27"),"Hackathon",LEVELS.Average,1,repository.findByValidationKey("AX7KXD")));
            scoresRepository.save(new Scores(new SimpleDateFormat("mm:ss").parse("00:42"),"Hackathon",LEVELS.Beginner,5,repository.findByValidationKey("HSD72K")));
            scoresRepository.save(new Scores(new SimpleDateFormat("mm:ss").parse("00:57"),"Hackathon",LEVELS.Average,5,repository.findByValidationKey("HSD72K")));
         *//**//*   // fetch all Users
           log.info("Users found with findAll():");
            log.info("-------------------------------");
            for (User User : repository.findAll()) {
                log.info(User.toString());
            }
            log.info("");

            // fetch an individual User by ID
            repository.findById(1)
                    .ifPresent(User -> {
                        log.info("User found with findById(1):");
                        log.info("--------------------------------");
                        log.info(User.toString());
                        log.info("");
                    });

            // fetch Users by last name
            log.info("User found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findAll().forEach(result -> {
                log.info(result.toString());
            });
            // for (User bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");
      *//**//*  };
   *//* }*/
}
