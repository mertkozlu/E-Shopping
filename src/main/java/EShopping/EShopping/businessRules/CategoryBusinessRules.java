package EShopping.EShopping.businessRules;

import EShopping.EShopping.dataAccess.CategoryRepository;
import EShopping.EShopping.exceptions.BusinessException;
import EShopping.EShopping.exceptions.CategoryNotFoundException;
import EShopping.EShopping.exceptions.UserNotFoundException;
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
