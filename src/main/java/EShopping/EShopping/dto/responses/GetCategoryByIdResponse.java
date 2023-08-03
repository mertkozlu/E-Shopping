package EShopping.EShopping.dto.responses;

import lombok.Data;

@Data
public class GetCategoryByIdResponse {
    private Long categoryId;
    private String categoryName;
}
