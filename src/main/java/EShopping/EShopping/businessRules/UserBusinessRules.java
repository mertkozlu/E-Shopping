package EShopping.EShopping.businessRules;

import EShopping.EShopping.dataAccess.UserRepository;
import EShopping.EShopping.exceptions.BusinessException;
import EShopping.EShopping.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserBusinessRules {
    private UserRepository userRepository;

    public void existsByUserName(String userName) {
        if (this.userRepository.existsByUserName(userName)) {
            throw new UserNotFoundException("User already available");
        }
    }

    public void existsByEmail(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new BusinessException("Email already available");
        }
    }

    public void createPassword(String password) {
        if (password.length() < 8) {
            throw new BusinessException("Password cannot be lass than 8 characters");
        }
    }
}
