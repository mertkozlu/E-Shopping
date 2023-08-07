package EShopping.EShopping.service;

import EShopping.EShopping.dataAccess.CartRepository;
import EShopping.EShopping.dto.requests.CreateCartRequest;
import EShopping.EShopping.dto.responses.GetAllCartResponse;
import EShopping.EShopping.entities.Cart;
import EShopping.EShopping.exceptions.BusinessException;
import EShopping.EShopping.mappers.ModelMapperService;
import EShopping.EShopping.result.*;
import EShopping.EShopping.rules.CartBusinessRules;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ModelMapperService modelMapperService;
    private final CartBusinessRules cartBusinessRules;

    public CartService(CartRepository cartRepository, ModelMapperService modelMapperService, CartBusinessRules cartBusinessRules) {
        this.cartRepository = cartRepository;
        this.modelMapperService = modelMapperService;
        this.cartBusinessRules = cartBusinessRules;
    }

    public DataResult<List<GetAllCartResponse>> getAllCart() {
        List<Cart> carts = cartRepository.findAll();
        List<GetAllCartResponse> getAllCartResponses = carts.stream()
                .map(cart -> this.modelMapperService.forResponse()
                        .map(cart, GetAllCartResponse.class)).collect(Collectors.toList());
        return new SuccessDataResult<>(getAllCartResponses, true,"Cart successfully listed.");
    }

    public Result createCart(CreateCartRequest newCart) {
        if (this.cartBusinessRules.validatedRequest(newCart)) {
            Cart cart = this.modelMapperService.forRequest().map(newCart, Cart.class);
            cartRepository.save(cart);

            return new SuccessResult("Cart successfully added.");
        }else
            return new ErrorResult("Cart could not added.");
    }

    public void deleteCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new BusinessException("Cart can not found."));
        this.cartRepository.delete(cart);
    }
}
