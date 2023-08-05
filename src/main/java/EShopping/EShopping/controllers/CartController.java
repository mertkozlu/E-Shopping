package EShopping.EShopping.controllers;

import EShopping.EShopping.dto.requests.CreateCartRequest;
import EShopping.EShopping.dto.responses.GetAllCartResponse;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.result.Result;
import EShopping.EShopping.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/getAll")
    public DataResult<List<GetAllCartResponse>> getAll(){
        return cartService.getAllCart();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Result createCart(@RequestBody @Validated CreateCartRequest newCart) {
        return cartService.createCart(newCart);
    }

    @DeleteMapping("/delete/{cartId}")
    public void deleteCart(@PathVariable Long cartId) {
        this.cartService.deleteCart(cartId);
    }
}
