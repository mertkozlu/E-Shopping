package EShopping.EShopping.dataAccess;

import EShopping.EShopping.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUser_UserId(Long userId);

    List<Comment> findByProduct_ProductId(Long productId);
}
