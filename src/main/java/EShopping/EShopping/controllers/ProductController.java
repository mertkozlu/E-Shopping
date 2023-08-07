package EShopping.EShopping.controllers;

import EShopping.EShopping.dto.requests.CreateProductRequest;
import EShopping.EShopping.dto.requests.UpdateProductRequest;
import EShopping.EShopping.dto.responses.GetAllProductResponse;
import EShopping.EShopping.dto.responses.GetProductByIdResponse;
import EShopping.EShopping.entities.Product;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.result.Result;
import EShopping.EShopping.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public DataResult<List<GetAllProductResponse>> getAll(Optional<Long> categoryId) {
        return productService.getAllProducts(categoryId);
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Result addProduct(@RequestBody @Validated CreateProductRequest newProduct) {
        return productService.addProduct(newProduct);
    }

    @GetMapping("/getById/{productId}")
    public ResponseEntity<GetProductByIdResponse> getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PutMapping("/update/{productId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId,
                                                 @RequestBody @Validated UpdateProductRequest updateProductRequest) {
        return productService.updateProduct(productId, updateProductRequest);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        this.productService.deleteProductById(productId);
    }
}
