package EShopping.EShopping.service;

import EShopping.EShopping.dataAccess.UserRepository;
import EShopping.EShopping.dto.GetUserByIdDto;
import EShopping.EShopping.dto.requests.CreateUserRequest;
import EShopping.EShopping.dto.requests.UpdateUserRequest;
import EShopping.EShopping.dto.responses.GetAllUserResponse;
import EShopping.EShopping.dto.responses.GetUserByIdResponse;
import EShopping.EShopping.entities.User;
import EShopping.EShopping.exceptions.BusinessException;
import EShopping.EShopping.mappers.ModelMapperService;
import EShopping.EShopping.result.*;
import EShopping.EShopping.rules.UserBusinessRules;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public GetUserByIdResponse getUserById(Long userId) {
        GetUserByIdResponse response = new GetUserByIdResponse();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new BusinessException("User can not found."));

        List<GetUserByIdDto> dtos = new ArrayList<>();
        dtos.add(convertUserGetUserByIdDto(user));

        response.setGetUserByIdDto(dtos);
        response.setResultCode("1");
        response.setResultDescription("Success");

        return response;

    }

    public User updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new BusinessException("User can not found."));
        if (Objects.nonNull(user)) {
            user.setUserName(updateUserRequest.getUserName());
            user.setEmail(updateUserRequest.getEmail());
            user.setPassword(updateUserRequest.getPassword());
            user.setBirthDate(updateUserRequest.getBirthDate());
            user.setAge(updateUserRequest.getAge());
        }

        return userRepository.save(user);
    }


    public void deleteUserById(Long userId) {
        this.userRepository.deleteById(userId);
    }

    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    GetUserByIdDto convertUserGetUserByIdDto(User user) {
        GetUserByIdDto getUserByIdDto = new GetUserByIdDto();
        getUserByIdDto.setUserId(user.getUserId());
        getUserByIdDto.setUserName(user.getUserName());
        getUserByIdDto.setEmail(user.getEmail());
        getUserByIdDto.setBirthDate(user.getBirthDate());
        getUserByIdDto.setAge(user.getAge());

        return getUserByIdDto;

    }

}
