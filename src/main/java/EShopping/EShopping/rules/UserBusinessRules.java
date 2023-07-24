package EShopping.EShopping.rules;

import EShopping.EShopping.dataAccess.UserRepository;
import EShopping.EShopping.dto.requests.CreateUserRequest;
import EShopping.EShopping.exceptions.BusinessException;
import EShopping.EShopping.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserBusinessRules {
    private UserRepository userRepository;

    public void existsByUserName(String userName) {
        if (this.userRepository.existsByUserName(userName)) {
            throw new UserNotFoundException("User name already exists !");
        }
    }

    public void existsByEmail(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new BusinessException("Email name already exists !");
        }
    }

    public boolean validateRequest(CreateUserRequest newUser) {
        boolean isSuccess = true;

        if (StringUtils.isEmpty(newUser.getUserName())) {
            isSuccess = false;
        }
        return isSuccess;
    }
}
