package EShopping.EShopping.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateProductRequest {
    @NotNull
    private Long categoryId;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 20)
    private String productName;
    @NotNull
    @NotBlank
    private String productDescription;
    @NotNull
    private double productPrice;
}
