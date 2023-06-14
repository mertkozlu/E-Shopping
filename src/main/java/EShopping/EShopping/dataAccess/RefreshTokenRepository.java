package EShopping.EShopping.dataAccess;

import EShopping.EShopping.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUser_UserId(Long userId);
}
