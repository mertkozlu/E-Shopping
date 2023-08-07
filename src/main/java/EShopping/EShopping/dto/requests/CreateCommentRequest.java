package EShopping.EShopping.dto.requests;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class CreateCommentRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
    @NotNull
    @Min(1)
    @Max(5)
    private int scoreStars;
    @NotNull
    @NotBlank
    private String text;
}
