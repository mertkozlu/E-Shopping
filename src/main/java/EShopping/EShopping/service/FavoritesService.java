package EShopping.EShopping.service;

import EShopping.EShopping.dataAccess.FavoritesRepository;
import EShopping.EShopping.dto.requests.CreateFavoritesRequest;
import EShopping.EShopping.dto.responses.GetAllFavoritesResponse;
import EShopping.EShopping.entities.Favorites;
import EShopping.EShopping.mappers.ModelMapperService;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.result.ErrorResult;
import EShopping.EShopping.result.Result;
import EShopping.EShopping.result.SuccessResult;
import EShopping.EShopping.rules.FavoritesBusinessRules;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final ModelMapperService modelMapperService;
    private final FavoritesBusinessRules favoritesBusinessRules;

    public FavoritesService(FavoritesRepository favoritesRepository, ModelMapperService modelMapperService, FavoritesBusinessRules favoritesBusinessRules) {
        this.favoritesRepository = favoritesRepository;
        this.modelMapperService = modelMapperService;
        this.favoritesBusinessRules = favoritesBusinessRules;
    }

    public DataResult<List<GetAllFavoritesResponse>> getAllFavorites() {
        List<Favorites> favorites = favoritesRepository.findAll();
        List<GetAllFavoritesResponse> getAllFavoritesResponses = favorites.stream()
                .map(favorite -> this.modelMapperService.forResponse()
                        .map(favorite, GetAllFavoritesResponse.class)).collect(Collectors.toList());

        return new DataResult<>(getAllFavoritesResponses, true, "Favorites successfully listed.");
    }

    public Result createFavorites(CreateFavoritesRequest newFavorites) {
        if (this.favoritesBusinessRules.validateRequest(newFavorites)) {
            Favorites favorites = this.modelMapperService.forRequest().map(newFavorites, Favorites.class);
            favoritesRepository.save(favorites);

            return new SuccessResult("Favorite successfully added.");
        } else
            return new ErrorResult("Favorite could not added.");
    }

    public void deleteFavoriteById(Long favoritesId) {
        this.favoritesRepository.deleteById(favoritesId);
    }
}
