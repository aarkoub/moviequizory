package aarkoub.moviequizory.domain.user;

import aarkoub.moviequizory.db.user.IUserRepository;
import aarkoub.moviequizory.exception.user.UserNotFoundException;
import aarkoub.moviequizory.domain.user.service.IUserService;
import aarkoub.moviequizory.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
public class UserServiceTest {

    @MockBean

    IUserRepository mockRepository;

    @Autowired
    IUserService userService;

    @Test
    public void retrieveUser() throws UserNotFoundException {

        Mockito.when(mockRepository.add()).thenReturn(new User(UUID.randomUUID()));

        User u = userService.retrieve(null);

        Mockito.when(mockRepository.find(u.getId())).thenReturn(u);
        Assertions.assertEquals(u, userService.retrieve(u.getId()));

        Mockito.when(mockRepository.find(UUID.randomUUID())).thenThrow(UserNotFoundException.class);
        Assertions.assertEquals(User.class, userService.retrieve(u.getId()).getClass());

    }

    @Test
    public void setHighscore() throws UserNotFoundException {

        User u = new User();
        Mockito.when(mockRepository.updateHighscore(u.getId(), 5)).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                u.setHighscore(5);
                return u;
            }
        });
        userService.setHighscore(u.getId(), 5);
        Assertions.assertEquals(5, u.getHighscore());
    }

}
