package EShopping.EShopping.service;

import EShopping.EShopping.dto.responses.GetAllCategoryResponse;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.result.SuccessDataResult;
import EShopping.EShopping.rules.CategoryBusinessRules;
import EShopping.EShopping.dataAccess.CategoryRepository;
import EShopping.EShopping.dto.requests.CreateCategoryRequest;
import EShopping.EShopping.entities.Category;
import EShopping.EShopping.mappers.ModelMapperService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public DataResult<List<GetAllCategoryResponse>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<GetAllCategoryResponse> getAllCategoryResponses = categories.stream()
                .map(category -> this.modelMapperService.forResponse()
                        .map(category, GetAllCategoryResponse.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<GetAllCategoryResponse>>
                (getAllCategoryResponses, true, "Categories successfully listed.");
    }

    public Category addCategory(CreateCategoryRequest newCategory) {
        this.categoryBusinessRules.existsByCategoryName(newCategory.getCategoryName());
        Category category = this.modelMapperService.forRequest().map(newCategory, Category.class);
        return categoryRepository.save(category);
    }
}
