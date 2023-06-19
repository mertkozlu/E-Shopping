package EShopping.EShopping.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class RegisterRequest {
    @NotNull
    @Size(min = 4, max = 20)
    private String userName;
    @NotNull
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
    @NotNull
    private String email;
    @NotNull
    private Date birthDate;
    @NotNull
    private int age;
}
