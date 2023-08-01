package EShopping.EShopping.controllers;

import EShopping.EShopping.dto.requests.CreateUserRequest;
import EShopping.EShopping.dto.requests.UpdateUserRequest;
import EShopping.EShopping.dto.responses.GetAllUserResponse;
import EShopping.EShopping.dto.responses.GetUserByIdResponse;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.result.Result;
import EShopping.EShopping.service.UserService;
import EShopping.EShopping.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public DataResult<List<GetAllUserResponse>> getAll() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Result createUser(@RequestBody @Validated CreateUserRequest newUser) {
        return userService.addUser(newUser);
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<GetUserByIdResponse> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/update/{userId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User updateUser(@PathVariable Long userId,@RequestBody @Validated UpdateUserRequest updateUserRequest) {
        return userService.updateUser(userId, updateUserRequest);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteUserById(userId);
    }


}
