package EShopping.EShopping.rules;

import EShopping.EShopping.dto.requests.CreateCommentRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CommentBusinessRules {

    public boolean validatedRequest(CreateCommentRequest newComment) {
        boolean isSuccess = true;

        if (Objects.isNull(newComment.getUserId()) || Objects.isNull(newComment.getProductId())) {
            isSuccess = false;
        }

        return isSuccess;
    }
}
