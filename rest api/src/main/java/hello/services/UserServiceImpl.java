package hello.services;

import hello.entities.User;

import hello.repositories.UserRepository;
import hello.utilities.KeyGenerator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getUserByValidationKey(String validationKey) {
        for (User user : userRepository.findAll())
            if (BCrypt.checkpw(validationKey, user.getValidationKey())) return user;
        return null;
    }

    @Override
    public boolean checkValidationKey(String validKey, String login) {
        User user = getUserByValidationKey(validKey);
        if (user != null && !user.getActivated() && user.getLogin().equals(login)) {
            user.setActivated(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public String registerNewUser(String name, String login, String nickname) {
        String generatedKey;
        KeyGenerator keyGenerator = new KeyGenerator();
        generatedKey = keyGenerator.generate(6);
        String encrypted = BCrypt.hashpw(generatedKey, BCrypt.gensalt());
        User u = new User(name, login, nickname, encrypted, false);
        userRepository.save(u);
        return generatedKey;
    }
}
