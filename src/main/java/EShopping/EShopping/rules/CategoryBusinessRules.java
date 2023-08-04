package EShopping.EShopping.rules;

import EShopping.EShopping.dataAccess.CategoryRepository;
import EShopping.EShopping.dto.requests.CreateCategoryRequest;
import EShopping.EShopping.exceptions.CategoryNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryBusinessRules {
    private  CategoryRepository categoryRepository;

    public void existsByCategoryName(String categoryName) {
        if (this.categoryRepository.existsByCategoryName(categoryName)) {
            throw new CategoryNotFoundException("Category name already exists !");
        }
    }

    public boolean validateRequest(CreateCategoryRequest newCategory) {
        boolean isSuccess = true;

        if (StringUtils.isEmpty(newCategory.getCategoryName())) {
            isSuccess = false;
        }
        return isSuccess;
    }
}
