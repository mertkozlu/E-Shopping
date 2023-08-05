package EShopping.EShopping.dto.responses;

import lombok.Data;

import java.util.Date;

@Data
public class GetAllCartResponse {
    private Long cartId;
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    Date createDate;


}
