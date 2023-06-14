package EShopping.EShopping.dto.responses;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {
    private Long userId;
    private String userName;
    private String email;
    private Date birthDate;
    private int age;
}
