package hello.services;

public interface UserService {

    boolean checkValidationKey( String validKey, String login);
    String registerNewUser(String name, String login,String nickname);
}
