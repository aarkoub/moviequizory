package aarkoub.moviequizory.db.user;

import aarkoub.moviequizory.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureTestEntityManager
@Transactional
public class UserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void add() {
        userRepository.add();
        Query q = testEntityManager.getEntityManager().createNativeQuery("select * from \"user\"");
        Assertions.assertEquals(1, q.getResultList().size());
    }

    @Test
    public void find() throws UserNotFoundException {
        User user = testEntityManager.persist(new User());
        testEntityManager.flush();
        Assertions.assertEquals(user.getId(), userRepository.find(user.getId()).getId());
        Assertions.assertThrows(UserNotFoundException.class, ()->userRepository.find(UUID.randomUUID()));
    }

    @Test
    public void updateHighScore() throws UserNotFoundException{
        User u = new User();
        testEntityManager.persist(u);
        testEntityManager.flush();
        Assertions.assertEquals(4, userRepository.updateHighscore(u.getId(), 4).getHighscore());
    }
}
