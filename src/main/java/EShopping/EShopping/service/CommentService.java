package EShopping.EShopping.service;

import EShopping.EShopping.dataAccess.CommentRepository;
import EShopping.EShopping.dataAccess.ProductRepository;
import EShopping.EShopping.dataAccess.UserRepository;
import EShopping.EShopping.dto.requests.CreateCommentRequest;
import EShopping.EShopping.dto.requests.UpdateCommentRequest;
import EShopping.EShopping.dto.responses.GetAllCommentResponse;
import EShopping.EShopping.dto.responses.GetCommentByIdResponse;
import EShopping.EShopping.entities.Comment;
import EShopping.EShopping.entities.Product;
import EShopping.EShopping.entities.User;
import EShopping.EShopping.exceptions.BusinessException;
import EShopping.EShopping.mappers.ModelMapperService;
import EShopping.EShopping.result.*;
import EShopping.EShopping.rules.CommentBusinessRules;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapperService modelMapperService;
    private final CommentBusinessRules commentBusinessRules;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CommentService(CommentRepository commentRepository, ModelMapperService modelMapperService, CommentBusinessRules commentBusinessRules, UserRepository userRepository, ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.modelMapperService = modelMapperService;
        this.commentBusinessRules = commentBusinessRules;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public DataResult<List<GetAllCommentResponse>> getAllComment(Optional<Long> userId, Optional<Long> productId) {
        List<Comment> list;
        if (userId.isPresent()) {
            list = commentRepository.findByUser_UserId(userId.get());
        }else if (productId.isPresent()) {
            list = commentRepository.findByProduct_ProductId(productId.get());
        }else
            list = commentRepository.findAll();

        List<GetAllCommentResponse> getAllCommentResponses = list.stream()
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

    public ResponseEntity<GetCommentByIdResponse> getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        GetCommentByIdResponse response = new GetCommentByIdResponse();
        response.setCommentId(comment.getCommentId());
        response.setUserId(comment.getUser().getUserId());
        response.setUserName(comment.getUser().getUserName());
        response.setProductId(comment.getProduct().getProductId());
        response.setProductName(comment.getProduct().getProductName());
        response.setScoreStars(comment.getScoreStars());
        response.setText(comment.getText());
        response.setCreateDate(comment.getCreateDate());

        ResponseEntity<GetCommentByIdResponse> result =  new ResponseEntity<>(response, HttpStatus.OK);
        return result;
    }

    public ResponseEntity<Comment> updateComment(Long commentId, UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (Objects.nonNull(comment)) {
            User user = userRepository.findById(updateCommentRequest.getUserId()).orElse(null);
            Product product = productRepository.findById(updateCommentRequest.getProductId()).orElse(null);
            if (Objects.nonNull(user) || Objects.nonNull(product)) {
                comment.setScoreStars(updateCommentRequest.getScoreStars());
                comment.setText(updateCommentRequest.getText());
                comment.setCreateDate(new Date());
                comment.setUser(user);
                comment.setProduct(product);

                Comment updateComment = commentRepository.save(comment);
                return new ResponseEntity<>(updateComment, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void deleteCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException("Comment can not found."));
        this.commentRepository.delete(comment);
    }
}
