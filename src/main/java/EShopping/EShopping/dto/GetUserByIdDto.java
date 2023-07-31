package EShopping.EShopping.dto;

import lombok.Data;

import java.util.Date;
@Data
public class GetUserByIdDto {
    private Long userId;
    private String userName;
    private String email;
    private Date birthDate;
    private int age;
}
