package EShopping.EShopping.dto.responses;

import lombok.Data;

import java.util.Date;

@Data
public class GetProductByIdResponse {
    private Long categoryId;
    private String categoryName;
    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    Date createDate;
}
