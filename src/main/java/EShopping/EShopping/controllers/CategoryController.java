package EShopping.EShopping.controllers;

import EShopping.EShopping.dto.requests.UpdateCategoryRequest;
import EShopping.EShopping.dto.responses.GetAllCategoryResponse;
import EShopping.EShopping.dto.responses.GetCategoryByIdResponse;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.result.Result;
import EShopping.EShopping.service.CategoryService;
import EShopping.EShopping.dto.requests.CreateCategoryRequest;
import EShopping.EShopping.entities.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAll")
    public DataResult<List<GetAllCategoryResponse>> getAll() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Result createCategory(@RequestBody @Validated CreateCategoryRequest newCategory) {
        return categoryService.addCategory(newCategory);
    }

    @GetMapping("/getById/{categoryId}")
    public ResponseEntity<GetCategoryByIdResponse> getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PutMapping("/update/{categoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId,
                                                   @RequestBody @Validated UpdateCategoryRequest updateCategoryRequest) {
        return categoryService.updateCategory(categoryId, updateCategoryRequest);
    }

    @DeleteMapping("/delete/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        this.categoryService.deleteCategory(categoryId);
    }


}
