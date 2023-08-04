package EShopping.EShopping.controllers;

import EShopping.EShopping.dto.requests.CreateProductRequest;
import EShopping.EShopping.dto.responses.GetAllProductResponse;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.result.Result;
import EShopping.EShopping.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public DataResult<List<GetAllProductResponse>> getAll() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public Result addProduct(@RequestBody CreateProductRequest newProduct) {
        return productService.addProduct(newProduct);
    }
}
