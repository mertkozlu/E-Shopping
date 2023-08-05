package EShopping.EShopping.dto.responses;

import lombok.Data;

import java.util.Date;

@Data
public class GetAllCommentResponse {
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;
    private Long commentId;
    private int scoreStars;
    private String text;
    Date createDate;
}
