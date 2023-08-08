package EShopping.EShopping.dataAccess;

import EShopping.EShopping.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryName(String categoryName);

    @Query(value = "Select count (*) from categories", nativeQuery = true)
    Integer countCategory();
}
