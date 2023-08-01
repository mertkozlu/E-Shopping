package EShopping.EShopping.service;

import EShopping.EShopping.dto.GetCategoryByIdDto;
import EShopping.EShopping.dto.responses.GetAllCategoryResponse;
import EShopping.EShopping.dto.responses.GetCategoryByIdResponse;
import EShopping.EShopping.exceptions.BusinessException;
import EShopping.EShopping.result.*;
import EShopping.EShopping.rules.CategoryBusinessRules;
import EShopping.EShopping.dataAccess.CategoryRepository;
import EShopping.EShopping.dto.requests.CreateCategoryRequest;
import EShopping.EShopping.entities.Category;
import EShopping.EShopping.mappers.ModelMapperService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Result addCategory(CreateCategoryRequest newCategory) {
        if (this.categoryBusinessRules.validateRequest(newCategory)) {
            Category category = this.modelMapperService.forRequest().map(newCategory, Category.class);
            this.categoryBusinessRules.existsByCategoryName(category.getCategoryName());
            categoryRepository.save(category);

            return new SuccessResult("Category successfully added.");
        }else
            return new ErrorResult("Category could not added.");
    }

    public GetCategoryByIdResponse getCategoryById(Long categoryId) {
        GetCategoryByIdResponse response = new GetCategoryByIdResponse();
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                ()-> new BusinessException("Category can not found."));

        List<GetCategoryByIdDto> dtos = new ArrayList<>();
        dtos.add(convertCategoryGetCategoryByIdDto(category));

        response.setGetCategoryByIdDto(dtos);
        response.setResultCode("1");
        response.setResultDescription("Success");

        return response;
    }

    public GetCategoryByIdDto convertCategoryGetCategoryByIdDto(Category category) {
        GetCategoryByIdDto getCategoryByIdDto = new GetCategoryByIdDto();
        getCategoryByIdDto.setCategoryId(category.getCategoryId());
        getCategoryByIdDto.setCategoryName(category.getCategoryName());

        return getCategoryByIdDto;
    }
}
