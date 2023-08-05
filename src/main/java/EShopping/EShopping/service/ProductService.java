package EShopping.EShopping.service;

import EShopping.EShopping.dataAccess.CategoryRepository;
import EShopping.EShopping.dataAccess.ProductRepository;
import EShopping.EShopping.dto.requests.CreateProductRequest;
import EShopping.EShopping.dto.requests.CreateUserRequest;
import EShopping.EShopping.dto.requests.UpdateProductRequest;
import EShopping.EShopping.dto.responses.GetAllProductResponse;
import EShopping.EShopping.dto.responses.GetProductByIdResponse;
import EShopping.EShopping.entities.Category;
import EShopping.EShopping.entities.Product;
import EShopping.EShopping.entities.User;
import EShopping.EShopping.exceptions.BusinessException;
import EShopping.EShopping.mappers.ModelMapperService;
import EShopping.EShopping.result.*;
import EShopping.EShopping.rules.ProductBusinessRules;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapperService modelMapperService;
    private final ProductBusinessRules productBusinessRules;
    private final CategoryRepository categoryRepository;


    public ProductService(ProductRepository productRepository, ModelMapperService modelMapperService, ProductBusinessRules productBusinessRules, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapperService = modelMapperService;
        this.productBusinessRules = productBusinessRules;
        this.categoryRepository = categoryRepository;
    }

    public DataResult<List<GetAllProductResponse>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<GetAllProductResponse> getAllProductResponses = products.stream()
                .map(product -> this.modelMapperService.forResponse()
                        .map(product, GetAllProductResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<GetAllProductResponse>>
                (getAllProductResponses, true, "Categories successfully listed.");
    }

    public Result addProduct(CreateProductRequest newProduct) {
        if (this.productBusinessRules.validatedRequest(newProduct)) {
            Product product = this.modelMapperService.forRequest().map(newProduct, Product.class);
            this.productBusinessRules.existsByProductName(product.getProductName());
            product.setCreateDate(new Date());
            productRepository.save(product);

            return new SuccessResult("Product successfully added.");
        } else
            return new ErrorResult("Product could not added.");
    }

    public ResponseEntity<GetProductByIdResponse> getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setCategoryId(product.getCategory().getCategoryId());
        response.setCategoryName(product.getCategory().getCategoryName());
        response.setProductId(product.getProductId());
        response.setProductName(product.getProductName());
        response.setProductDescription(product.getProductDescription());
        response.setProductPrice(product.getProductPrice());

        ResponseEntity<GetProductByIdResponse> result = new ResponseEntity<>(response, HttpStatus.OK);
        return result;
    }

    public ResponseEntity<Product> updateProduct(Long productId, UpdateProductRequest updateProductRequest) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new BusinessException("Product can not found."));
        if (Objects.nonNull(product)) {
            Category category = categoryRepository.findById(updateProductRequest.getCategoryId()).orElse(null);
            if (Objects.nonNull(category)) {
                product.setProductName(updateProductRequest.getProductName());
                product.setProductDescription(updateProductRequest.getProductDescription());
                product.setProductPrice(updateProductRequest.getProductPrice());
                product.setCreateDate(new Date());
                product.setCategory(category);

                Product updateProduct = productRepository.save(product);
                return new ResponseEntity<>(updateProduct, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void deleteUpdate(Long productId) {
        this.productRepository.deleteById(productId);
    }
}
