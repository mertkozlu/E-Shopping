package EShopping.EShopping.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateFavoritesRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
}
