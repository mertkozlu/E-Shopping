package EShopping.EShopping.dataAccess;

import EShopping.EShopping.entities.Comment;
import EShopping.EShopping.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    User findByUserName(String username);

}
