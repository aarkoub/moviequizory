package aarkoub.moviequizory.domain.user.service;

import aarkoub.moviequizory.db.user.IUserRepository;
import aarkoub.moviequizory.db.user.UserNotFoundException;
import aarkoub.moviequizory.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public User retrieve(@Nullable UUID userId) {
        if (userId != null) {
            try {
                return userRepository.find(userId);
            } catch (UserNotFoundException e) {
                return userRepository.add();
            }
        }
        return userRepository.add();
    }

    @Override
    public User setHighscore(UUID id, int highscore) throws UserNotFoundException {
        return userRepository.updateHighscore(id, highscore);
    }
}
