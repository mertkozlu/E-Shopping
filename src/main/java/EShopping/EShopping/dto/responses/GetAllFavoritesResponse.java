package EShopping.EShopping.dto.responses;

import lombok.Data;


@Data
public class GetAllFavoritesResponse {
    private Long favoritesId;
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;

}
