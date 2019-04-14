package test;

import hello.Application;
import hello.utilities.enums.LEVELS;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Transactional
public class MainControllerTests {

 @Autowired
    private MockMvc mockMvc;
    private static String generatedKey;

   @Test
   public void ShouldReturnMoreThanOneJSONObjectInJSONArrayWhenKeyIsValid() throws Exception {
       this.mockMvc.perform(get("http://localhost:8080/demo/all").param("validationKey", "A7XKXD").param("level", String.valueOf(LEVELS.Beginner))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.[0]").isNotEmpty()).andExpect(jsonPath("$.[1]").isNotEmpty());
   }
    @Test
    public void ShouldReturnOneScoreObjectWhenKeyIsValid() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/demo/bestScoreByLevel")
                .param("validationKey", "A7XKXD")
                .param("level", String.valueOf(LEVELS.Beginner)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$")
                        .value(Matchers.instanceOf(LinkedHashMap.class)));
    }
    @Test
    public void ShouldReturnTrueWhenAddingNewScoreEndedSuccessfully() throws Exception {
        this.mockMvc.perform(post("http://localhost:8080/demo/add")
                .param("score", "00:00:33")
                .param("pack", "Hackathon")
                .param("validationKey", "A7XKXD")
                .param("level", String.valueOf(LEVELS.Beginner)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").isBoolean())
                .andExpect(jsonPath("$").value(Matchers.equalTo(true)));
    }
    @Test
    public void ShouldReturnGeneratedKey() throws Exception {
        MvcResult result= this.mockMvc.perform(post("http://localhost:8080/demo/register")
                .param("name", "RandomName")
                .param("nickname", "NicknameBeingShownInHighscoreList")
                .param("login", "Random12"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").isString())
        .andReturn();
       if (result.getResponse().getContentLength()!=6)
               throw new Exception("content length is not equal to 6");

    }

    @Test
    public void ShouldReturnTrueWhenKeyWasNotActivatedAndHasBeenValidatedSuccessfully() throws Exception {

        this.mockMvc.perform(post("http://localhost:8080/demo/validateKey")
                .param("validKey", "8NARA7")
                .param("login", "Tester")) //Tester
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").isBoolean())
                .andExpect(jsonPath("$").value(Matchers.equalTo(true))); //returns true if validation is successful
    }
}