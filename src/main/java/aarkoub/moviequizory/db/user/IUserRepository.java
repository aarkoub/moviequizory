package aarkoub.moviequizory.db.user;

import aarkoub.moviequizory.domain.user.User;
import aarkoub.moviequizory.exception.user.UserNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository {
    User add();
    User find(UUID id) throws UserNotFoundException;
    User updateHighscore(UUID id, int highscore) throws UserNotFoundException;
}
