package EShopping.EShopping.service;

import EShopping.EShopping.rules.CategoryBusinessRules;
import EShopping.EShopping.dataAccess.CategoryRepository;
import EShopping.EShopping.dto.requests.CreateCategoryRequest;
import EShopping.EShopping.entities.Category;
import EShopping.EShopping.mappers.ModelMapperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapperService modelMapperService;
    private final CategoryBusinessRules categoryBusinessRules;

    public CategoryService(CategoryRepository categoryRepository, ModelMapperService modelMapperService,
                           CategoryBusinessRules categoryBusinessRules) {
        this.categoryRepository = categoryRepository;
        this.modelMapperService = modelMapperService;
        this.categoryBusinessRules = categoryBusinessRules;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(CreateCategoryRequest newCategory) {
        this.categoryBusinessRules.existsByCategoryName(newCategory.getCategoryName());
        Category category = this.modelMapperService.forRequest().map(newCategory, Category.class);
        return categoryRepository.save(category);
    }
}
