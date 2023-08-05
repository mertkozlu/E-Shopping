package EShopping.EShopping.service;

import EShopping.EShopping.dataAccess.CommentRepository;
import EShopping.EShopping.dto.responses.GetAllCommentResponse;
import EShopping.EShopping.entities.Comment;
import EShopping.EShopping.mappers.ModelMapperService;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.result.SuccessDataResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapperService modelMapperService;

    public CommentService(CommentRepository commentRepository, ModelMapperService modelMapperService) {
        this.commentRepository = commentRepository;
        this.modelMapperService = modelMapperService;
    }

    public DataResult<List<GetAllCommentResponse>> getAllComment() {
        List<Comment> comments = commentRepository.findAll();
        List<GetAllCommentResponse> getAllCommentResponses = comments.stream()
                .map(comment -> this.modelMapperService.forResponse()
                        .map(comment, GetAllCommentResponse.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(getAllCommentResponses, true, "Comment successfully listed.");
    }
}
