package EShopping.EShopping.dto;

import lombok.Data;

@Data
public class GetCategoryByIdDto {
    private Long categoryId;
    private String categoryName;
}
