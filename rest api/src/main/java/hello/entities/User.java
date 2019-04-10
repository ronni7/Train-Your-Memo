package hello.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    public User() {
    }

    public User(String name, String login, String nickname, String validationKey, Boolean activated) {
        this.name = name;
        this.login = login;
        this.nickname = nickname;
        this.validationKey = validationKey;
        this.activated = activated;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", nickname='" + nickname + '\'' +
                ", validationKey='" + validationKey + '\'' +
                ", activated=" + activated +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String login;

    private String nickname;

    private String validationKey;

    private Boolean activated;

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Integer getId() {
        return id;
    }
    public String getValidationKey() {
        return validationKey;
    }

    /*public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }



    public void setValidationKey(String validationKey) {
        this.validationKey = validationKey;
    }*/
}

