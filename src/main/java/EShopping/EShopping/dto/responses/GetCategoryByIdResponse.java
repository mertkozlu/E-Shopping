package EShopping.EShopping.dto.responses;

import EShopping.EShopping.dto.GetCategoryByIdDto;
import lombok.Data;

import java.util.List;
@Data
public class GetCategoryByIdResponse extends BaseResponse {
    List<GetCategoryByIdDto> getCategoryByIdDto;
}
