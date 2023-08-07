package EShopping.EShopping.dto.requests;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCommentRequest {
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
