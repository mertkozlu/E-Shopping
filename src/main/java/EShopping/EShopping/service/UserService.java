package EShopping.EShopping.service;

import EShopping.EShopping.dto.requests.CreateUserRequest;
import EShopping.EShopping.dto.responses.GetAllUserResponse;
import EShopping.EShopping.result.*;
import EShopping.EShopping.rules.UserBusinessRules;
import EShopping.EShopping.dataAccess.UserRepository;
import EShopping.EShopping.entities.User;
import EShopping.EShopping.mappers.ModelMapperService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public DataResult<List<GetAllUserResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<GetAllUserResponse> getAllUserResponses = users.stream()
                .map(user -> this.modelMapperService.forResponse()
                        .map(user, GetAllUserResponse.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<GetAllUserResponse>>
                (getAllUserResponses, true, "Users successfully listed.");
    }

    public Result addUser(CreateUserRequest newUser) {
        if (this.userBusinessRules.validateRequest(newUser)) {
            User user = this.modelMapperService.forRequest().map(newUser, User.class);
            this.userBusinessRules.existsByUserName(user.getUserName());
            userRepository.save(user);

            return new SuccessResult("User successfully added.");
        }else
            return new ErrorResult("User could not be added.");
    }

    public User getOneUserByUserName(String userName) {
        return  userRepository.findByUserName(userName);
    }
}
