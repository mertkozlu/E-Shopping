package EShopping.EShopping.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateCategoryRequest {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 20)
    private String categoryName;
}
