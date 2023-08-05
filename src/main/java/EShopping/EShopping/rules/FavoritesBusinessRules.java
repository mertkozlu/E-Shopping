package EShopping.EShopping.rules;

import EShopping.EShopping.dto.requests.CreateFavoritesRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class FavoritesBusinessRules {
    public boolean validateRequest(CreateFavoritesRequest newFavorites) {
        boolean isSuccess = true;
        if (Objects.isNull(newFavorites.getUserId()) || Objects.isNull(newFavorites.getProductId())) {
            isSuccess = false;
        }
        return isSuccess;
    }
}
