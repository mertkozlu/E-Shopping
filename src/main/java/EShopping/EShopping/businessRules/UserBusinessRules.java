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
            throw new UserNotFoundException("User name already exists !");
        }
    }

    public void existsByEmail(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new BusinessException("Email name already exists !");
        }
    }
}
