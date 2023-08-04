package EShopping.EShopping.dataAccess;

import EShopping.EShopping.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Long> {
}
