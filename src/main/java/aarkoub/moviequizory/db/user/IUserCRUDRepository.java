package aarkoub.moviequizory.db.user;

import aarkoub.moviequizory.domain.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserCRUDRepository extends CrudRepository<User, UUID> {

    @Modifying(clearAutomatically = true)
    @Query(value="UPDATE \"user\" SET highscore=?2 WHERE id=?1", nativeQuery = true)
    int updateHighscore(UUID userId, int highscore);

}
