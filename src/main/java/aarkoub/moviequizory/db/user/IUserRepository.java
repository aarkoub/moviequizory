package aarkoub.moviequizory.db.user;

import aarkoub.moviequizory.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface IUserRepository {
    User add();
    User find(UUID id) throws UserNotFoundException;
    User updateHighscore(UUID id, int highscore) throws UserNotFoundException;
}
