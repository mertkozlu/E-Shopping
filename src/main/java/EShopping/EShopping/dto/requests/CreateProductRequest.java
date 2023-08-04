package EShopping.EShopping.dto.requests;

import lombok.Data;

@Data
public class CreateProductRequest {
    private Long categoryId;
    private String productName;
    private String productDescription;
    private double productPrice;
}
