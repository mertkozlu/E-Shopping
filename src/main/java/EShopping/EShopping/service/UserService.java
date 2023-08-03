package EShopping.EShopping.service;

import EShopping.EShopping.dataAccess.UserRepository;
import EShopping.EShopping.dto.requests.CreateUserRequest;
import EShopping.EShopping.dto.requests.UpdateUserRequest;
import EShopping.EShopping.dto.responses.GetAllUserResponse;
import EShopping.EShopping.dto.responses.GetUserByIdResponse;
import EShopping.EShopping.entities.User;
import EShopping.EShopping.exceptions.BusinessException;
import EShopping.EShopping.mappers.ModelMapperService;
import EShopping.EShopping.result.*;
import EShopping.EShopping.rules.UserBusinessRules;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserBusinessRules userBusinessRules;
    private final ModelMapperService modelMapperService;

    public UserService(UserRepository userRepository, UserBusinessRules userBusinessRules,
                       ModelMapperService modelMapperService) {
        this.userRepository = userRepository;
        this.userBusinessRules = userBusinessRules;
        this.modelMapperService = modelMapperService;
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
        } else
            return new ErrorResult("User could not be added.");
    }

    public ResponseEntity<GetUserByIdResponse> getUserById(Long userId) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        GetUserByIdResponse response = new GetUserByIdResponse();
        response.setUserId(user.getUserId());
        response.setUserName(user.getUserName());
        response.setEmail(user.getEmail());
        response.setBirthDate(user.getBirthDate());
        response.setAge(user.getAge());

        ResponseEntity<GetUserByIdResponse> result = new ResponseEntity<>(response,
                HttpStatus.OK);
        return result;

    }

    public ResponseEntity<User> updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId).orElse(null);
        if (Objects.nonNull(user)) {
            user.setUserName(updateUserRequest.getUserName());
            user.setEmail(updateUserRequest.getEmail());
            user.setPassword(updateUserRequest.getPassword());
            user.setBirthDate(updateUserRequest.getBirthDate());
            user.setAge(updateUserRequest.getAge());

            User updateUser = userRepository.save(user);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public void deleteUserById(Long userId) {
        this.userRepository.deleteById(userId);
    }

    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
