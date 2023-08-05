package EShopping.EShopping.rules;

import EShopping.EShopping.dataAccess.ProductRepository;
import EShopping.EShopping.dto.requests.CreateProductRequest;
import EShopping.EShopping.exceptions.CategoryNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductBusinessRules {
    private ProductRepository productRepository;
    public boolean validatedRequest(CreateProductRequest newProduct) {
        boolean isSuccess = true;

        if (StringUtils.isEmpty(newProduct.getProductName())) {
            isSuccess = false;
        }
        return isSuccess;
    }

    public void existsByProductName(String productName) {
        if (this.productRepository.existsByProductName(productName)) {
            throw new CategoryNotFoundException("Product name already exists !");
        }
    }

}
