package EShopping.EShopping.dataAccess;

import EShopping.EShopping.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductName(String productName);

    List<Product> findByCategory_CategoryId(Long categoryId);

}
