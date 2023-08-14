package EShopping.EShopping.dataAccess;

import EShopping.EShopping.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser_UserId(Long userId);

    List<Cart> findByProduct_ProductId(Optional<Long> productId);
}
