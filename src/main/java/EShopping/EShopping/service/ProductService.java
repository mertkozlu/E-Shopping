package EShopping.EShopping.service;

import EShopping.EShopping.dataAccess.ProductRepository;
import EShopping.EShopping.dto.requests.CreateProductRequest;
import EShopping.EShopping.dto.responses.GetAllProductResponse;
import EShopping.EShopping.entities.Product;
import EShopping.EShopping.mappers.ModelMapperService;
import EShopping.EShopping.result.*;
import EShopping.EShopping.rules.ProductBusinessRules;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapperService modelMapperService;
    private final ProductBusinessRules productBusinessRules;


    public ProductService(ProductRepository productRepository, ModelMapperService modelMapperService, ProductBusinessRules productBusinessRules) {
        this.productRepository = productRepository;
        this.modelMapperService = modelMapperService;
        this.productBusinessRules = productBusinessRules;
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
            newProduct.setProductDescription(product.getProductDescription());
            newProduct.setProductPrice(product.getProductPrice());
            product.setCreateDate(new Date());
            productRepository.save(product);

            return new SuccessResult("Product successfully added.");
        } else
            return new ErrorResult("Product could not added.");
    }
}
