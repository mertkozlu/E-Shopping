package EShopping.EShopping.rules;

import EShopping.EShopping.dataAccess.CategoryRepository;
import EShopping.EShopping.exceptions.CategoryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryBusinessRules {
    private final CategoryRepository categoryRepository;

    public void existsByCategoryName(String categoryName) {
        if (this.categoryRepository.existsByCategoryName(categoryName)) {
            throw new CategoryNotFoundException("Category name already exists !");
        }
    }
}
