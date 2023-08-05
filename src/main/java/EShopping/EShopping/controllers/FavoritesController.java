package EShopping.EShopping.controllers;

import EShopping.EShopping.dto.requests.CreateFavoritesRequest;
import EShopping.EShopping.dto.responses.GetAllFavoritesResponse;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.result.Result;
import EShopping.EShopping.service.FavoritesService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {
    private final FavoritesService favoritesService;

    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    @GetMapping("/getAll")
    public DataResult<List<GetAllFavoritesResponse>> getAll() {
        return favoritesService.getAllFavorites();
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Result createFavorites(@RequestBody @Validated CreateFavoritesRequest newFavorites) {
        return favoritesService.createFavorites(newFavorites);
    }

    @DeleteMapping("/delete/{favoritesId}")
    public void deleteFavorites(@PathVariable Long favoritesId) {
        this.favoritesService.deleteFavoriteById(favoritesId);
    }
}
