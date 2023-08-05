package EShopping.EShopping.dataAccess;

import EShopping.EShopping.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
