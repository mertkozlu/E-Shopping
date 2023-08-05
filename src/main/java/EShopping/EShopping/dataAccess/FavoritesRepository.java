package EShopping.EShopping.dataAccess;

import EShopping.EShopping.entities.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
}
