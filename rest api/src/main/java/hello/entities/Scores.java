package hello.entities;

import javax.persistence.*;

import java.io.Serializable;

import java.util.Date;

@Entity
public class Scores implements Serializable {
    public Scores() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer userID;
    @Temporal(TemporalType.TIME)
    private Date score;
    private String pack;
   private String  level;


    public Scores(Integer userID, Date score, String pack, String level) {
        this.userID = userID;
        this.score = score;
        this.pack = pack;
        this.level = level;
    }

    @Override
    public String toString() {
        return "Id= " + id + "userID " +userID +" score= "+score +"  level=  "+level +" pack "+ pack;
    }

 /*   public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getScore() {
        return score;
    }

    public void setScore(Date score) {
        this.score = score;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }*/




}
