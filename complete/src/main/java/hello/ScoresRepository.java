package hello;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.sql.Time;
import java.util.Date;
import java.util.List;


public interface ScoresRepository extends CrudRepository<Scores,Integer> {

  List<Scores> findAll() ;

  @Query(value = "SELECT s.score,s.pack FROM Scores s  WHERE s.userID =:ID AND s.level=:level ORDER BY s.score LIMIT 1",nativeQuery = true)
    BestScore findScoresById( Integer ID,String level );

  @Query(value = "SELECT s.score,s.pack,s.level,u.nickname FROM Scores s INNER JOIN User u ON(s.userID=u.id) WHERE s.level=:l ORDER BY s.score ",nativeQuery = true)
  //@Value(value = "ListViewEntity")
  List<ListViewMapped> findAllByLevel(String l) ;
/*
  @Query(value = "INSERT INTO Scores VALUES(null,:level,:pack,:score,:userID)",nativeQuery = true)
  boolean addNewScore(LEVELS level, String pack, Date score, Integer userID);*/
    /* @Query(value = "SELECT s.score,s.pack,s.level,u.nickname FROM Scores s INNER JOIN User u on(s.userID=u.id) WHERE s.level=:l ORDER BY s.score ")
      @SqlResultSetMapping(name="ListViewEntity",classes= {@ConstructorResult(targetClass = ListViewEntity.class,columns={
              @ColumnResult(name = "nickname"),@ColumnResult(name = "score"),@ColumnResult(name = "pack"),@ColumnResult(name = "level")})})
      List<ListViewEntity> findAllByLevel(String l);*/


    /* @Query(value = "SELECT s.score,s.pack,s.level,u.nickname FROM Scores s INNER JOIN User u on(s.userID=u.id) WHERE s.level=:l ORDER BY s.score ")
          @SqlResultSetMapping(name="ListViewEntity",classes= {@ConstructorResult(targetClass = ListViewEntity.class,columns={
                  @ColumnResult(name = "nickname"),@ColumnResult(name = "score"),@ColumnResult(name = "pack"),@ColumnResult(name = "level")})})
          List<ListViewEntity> findAllByLevel(String l);*/




    /*   @Query(value = "SELECT s.score,s.pack,s.level,u.nickname FROM Scores s INNER JOIN User u on(s.userID=u.id) WHERE s.level=:l ORDER BY s.score ", nativeQuery = true)
       List<Object> findAllByLevel(String l);*/
   // Scores findScoresByUserValidationKey(String ValidationKey);
   // Scores findScoresByUser(User u);
 // @Query(value = "SELECT s.score,s.pack,s.level,u.nickname FROM Scores s INNER JOIN User u on(s.userID=u.id) WHERE s.userID =:ID ORDER BY s.score LIMIT 1",nativeQuery = true)

    // TODO: 2019-01-28 that query   SELECT s.level,s.pack,s.score,u.nickname FROM scores s INNER JOIN user u ON s.userid=u.id WHERE u.validation_key="AX7KXD"  ORDER BY score;
}
