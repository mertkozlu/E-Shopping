package EShopping.EShopping.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String password;
    private String email;
    private Date birthDate;
    private int age;

    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Favorites> favorites;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Cart> carts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Comment> comments;
}
