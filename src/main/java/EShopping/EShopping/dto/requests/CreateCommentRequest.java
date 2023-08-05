package EShopping.EShopping.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateCommentRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
    @NotNull
    @NotBlank
    @Size(min = 1, max = 5)
    private int scoreStars;
    @NotNull
    @NotBlank
    private String text;
}
