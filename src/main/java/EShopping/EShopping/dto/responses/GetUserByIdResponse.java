package EShopping.EShopping.dto.responses;

import EShopping.EShopping.dto.GetUserByIdDto;
import lombok.Data;

import java.util.List;

@Data
public class GetUserByIdResponse extends BaseResponse {
    List<GetUserByIdDto> getUserByIdDto;

}
