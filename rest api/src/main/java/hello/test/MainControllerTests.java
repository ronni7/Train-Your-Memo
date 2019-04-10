package hello.test;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hello.entities.dataObjects.BestScore;
import hello.utilities.enums.LEVELS;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTests {

    @Autowired
    private MockMvc mockMvc;
    private static String generatedKey;
  /*  @Test
    public void ShouldReturnJsonWhenKeyIsValid() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/demo/all").param("validationKey", "A7XKXD").param("level", String.valueOf(LEVELS.Beginner))).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty());
    }*/

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
                .param("nickname", "NicknameShowingInHighscoreList")
                .param("login", "Random12"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").isString())
        .andReturn();
       if (result.getResponse().getContentLength()==6)
           generatedKey=result.getResponse().getContentAsString();
           else
               throw new Exception("content length is not equal to 6");
    }
 //this test needs ShouldReturnGeneratedKey passed, because iut uses fresh new generated key, which cannot be resolved from hashed one

    @Test
    public void ShouldReturnTrueWhenKeyWasNotActivatedAndHasBeenValidatedSuccessfully() throws Exception {
        this.mockMvc.perform(post("http://localhost:8080/demo/validateKey")
                .param("validKey", MainControllerTests.generatedKey))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").isBoolean())
                .andExpect(jsonPath("$").value(Matchers.equalTo(true)));
    }
/*
    @Test
    public void noParamGreetingShouldReturnDefaultMessage() throws Exception {

        this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, World!"));
    }

    @Test
    public void paramGreetingShouldReturnTailoredMessage() throws Exception {

        this.mockMvc.perform(get("/greeting").param("name", "Spring Community"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
    }*/

}