package EShopping.EShopping.business;

import EShopping.EShopping.businessRules.UserBusinessRules;
import EShopping.EShopping.dataAccess.UserRepository;
import EShopping.EShopping.dto.requests.CreateUserRequest;
import EShopping.EShopping.dto.responses.UserResponse;
import EShopping.EShopping.entities.User;
import EShopping.EShopping.mappers.ModelMapperService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserBusinessRules userBusinessRules;
    private final ModelMapperService modelMapperService;

    public UserService (UserRepository userRepository, UserBusinessRules userBusinessRules,
                        ModelMapperService modelMapperService) {
        this.userRepository = userRepository;
        this.userBusinessRules = userBusinessRules;
        this.modelMapperService  = modelMapperService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(CreateUserRequest newUser) {
        this.userBusinessRules.existsByUserName(newUser.getUserName());
        this.userBusinessRules.existsByEmail(newUser.getEmail());
        this.userBusinessRules.createPassword(newUser.getPassword());
        User user = this.modelMapperService.forRequest().map(newUser, User.class);
        return userRepository.save(user);
    }
}
