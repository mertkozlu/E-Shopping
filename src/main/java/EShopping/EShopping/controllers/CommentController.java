package EShopping.EShopping.controllers;

import EShopping.EShopping.dto.requests.CreateCommentRequest;
import EShopping.EShopping.dto.requests.UpdateCommentRequest;
import EShopping.EShopping.dto.responses.GetAllCommentResponse;
import EShopping.EShopping.dto.responses.GetCommentByIdResponse;
import EShopping.EShopping.entities.Comment;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.result.Result;
import EShopping.EShopping.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getAll")
    public DataResult<List<GetAllCommentResponse>> getAll() {
        return commentService.getAllComment();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Result createComment(@RequestBody @Validated CreateCommentRequest newComment) {
        return commentService.createComment(newComment);
    }

    @GetMapping("getById/{commentId}")
    public ResponseEntity<GetCommentByIdResponse> getCommentById(@PathVariable Long commentId) {
        return commentService.getCommentById(commentId);
    }

    @PutMapping("update/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId,
                                                 @RequestBody @Validated UpdateCommentRequest updateCommentRequest) {
        return commentService.updateComment(commentId, updateCommentRequest);
    }

    @DeleteMapping("delete/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        this.commentService.deleteCommentById(commentId);
    }


}
