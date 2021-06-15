package aarkoub.moviequizory;

import aarkoub.moviequizory.domain.question.Question;
import aarkoub.moviequizory.domain.user.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureTestEntityManager
@Transactional
class MovieQuizoryAPITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void createUser() throws Exception {
        mockMvc.perform(get("/users/create"))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("userId"));
    }

    @Test
    void getUser() throws Exception {

        UUID id = testEntityManager.persist(new User()).getId();
        Cookie cookie = new Cookie("userId", id.toString());
        mockMvc.perform(get("/users/" + id).cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(cookie().value("userId", id.toString()));
    }

    @Test
    void setHighscore() throws Exception {

        UUID id = testEntityManager.persist(new User()).getId();
        Cookie cookie = new Cookie("userId", id.toString());
        mockMvc.perform(post("/users/" + id + "/setHighscore")
                .cookie(cookie)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"new_highscore\":8}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.highscore", is(8)));
    }

    @Test
    void generateQuiz() throws Exception {

        mockMvc.perform(get("/quizzes/generate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questions", Matchers.hasSize(100)));
    }

}
