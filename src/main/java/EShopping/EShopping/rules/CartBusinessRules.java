package EShopping.EShopping.rules;

import EShopping.EShopping.dto.requests.CreateCartRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CartBusinessRules {
    public boolean validatedRequest(CreateCartRequest newCart) {
        boolean isSuccess = true;
        if (Objects.isNull(newCart.getUserId()) || Objects.isNull(newCart.getProductId())) {
            isSuccess = false;
        }
        return isSuccess;
    }
}
