package EShopping.EShopping.service;

import EShopping.EShopping.dataAccess.CommentRepository;
import EShopping.EShopping.dto.requests.CreateCommentRequest;
import EShopping.EShopping.dto.responses.GetAllCommentResponse;
import EShopping.EShopping.entities.Comment;
import EShopping.EShopping.mappers.ModelMapperService;
import EShopping.EShopping.result.*;
import EShopping.EShopping.rules.CommentBusinessRules;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapperService modelMapperService;
    private final CommentBusinessRules commentBusinessRules;

    public CommentService(CommentRepository commentRepository, ModelMapperService modelMapperService, CommentBusinessRules commentBusinessRules) {
        this.commentRepository = commentRepository;
        this.modelMapperService = modelMapperService;
        this.commentBusinessRules = commentBusinessRules;
    }

    public DataResult<List<GetAllCommentResponse>> getAllComment() {
        List<Comment> comments = commentRepository.findAll();
        List<GetAllCommentResponse> getAllCommentResponses = comments.stream()
                .map(comment -> this.modelMapperService.forResponse()
                        .map(comment, GetAllCommentResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(getAllCommentResponses, true, "Comment successfully listed.");
    }

    public Result createComment(CreateCommentRequest newComment) {
        if (this.commentBusinessRules.validatedRequest(newComment)) {
            Comment comment = this.modelMapperService.forRequest().map(newComment, Comment.class);
            comment.setCreateDate(new Date());
            commentRepository.save(comment);

            return new SuccessResult("Comment successfully added.");
        } else
            return new ErrorResult("Comment could not added.");
    }
}
