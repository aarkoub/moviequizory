package aarkoub.moviequizory.domain.user.service;

import aarkoub.moviequizory.db.user.UserNotFoundException;
import aarkoub.moviequizory.domain.user.User;
import org.springframework.lang.Nullable;

import java.util.UUID;

public interface IUserService {

    User retrieve(@Nullable UUID userId);
    User setHighscore(UUID userId, int highscore) throws UserNotFoundException;
}
