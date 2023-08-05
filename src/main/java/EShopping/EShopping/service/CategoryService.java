package EShopping.EShopping.service;

import EShopping.EShopping.dataAccess.CategoryRepository;
import EShopping.EShopping.dto.requests.CreateCategoryRequest;
import EShopping.EShopping.dto.requests.UpdateCategoryRequest;
import EShopping.EShopping.dto.responses.GetAllCategoryResponse;
import EShopping.EShopping.dto.responses.GetCategoryByIdResponse;
import EShopping.EShopping.entities.Category;
import EShopping.EShopping.mappers.ModelMapperService;
import EShopping.EShopping.result.*;
import EShopping.EShopping.rules.CategoryBusinessRules;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public Result addCategory(CreateCategoryRequest newCategory) {
        if (this.categoryBusinessRules.validateRequest(newCategory)) {
            Category category = this.modelMapperService.forRequest().map(newCategory, Category.class);
            this.categoryBusinessRules.existsByCategoryName(category.getCategoryName());
            categoryRepository.save(category);

            return new SuccessResult("Category successfully added.");
        } else
            return new ErrorResult("Category could not added.");
    }

    public ResponseEntity<GetCategoryByIdResponse> getCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GetCategoryByIdResponse response = new GetCategoryByIdResponse();
        response.setCategoryId(category.getCategoryId());
        response.setCategoryName(category.getCategoryName());

        ResponseEntity<GetCategoryByIdResponse> result = new ResponseEntity<>(response, HttpStatus.OK);
        return result;
    }

    public ResponseEntity<Category> updateCategory(Long categoryId, UpdateCategoryRequest updateCategoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (Objects.nonNull(category)) {
            category.setCategoryName(updateCategoryRequest.getCategoryName());

            Category updateCategory = categoryRepository.save(category);
            return new ResponseEntity<>(updateCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void deleteCategory(Long categoryId) {
        this.categoryRepository.deleteById(categoryId);
    }
}
