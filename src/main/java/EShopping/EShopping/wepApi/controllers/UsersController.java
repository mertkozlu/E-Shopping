package EShopping.EShopping.wepApi.controllers;

import EShopping.EShopping.business.UserService;
import EShopping.EShopping.dto.requests.CreateUserRequest;
import EShopping.EShopping.entities.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;

    public UsersController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public List<User> getAll() {
        return userService.getAllUsers();
    }

    @PostMapping()
    public User createOneUser(@RequestBody CreateUserRequest newUser) {
        return userService.saveOneUser(newUser);
    }
}
