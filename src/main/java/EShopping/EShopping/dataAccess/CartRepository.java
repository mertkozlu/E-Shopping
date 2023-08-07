package EShopping.EShopping.dataAccess;

import EShopping.EShopping.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser_UserId(Long userId);
}
