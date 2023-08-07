package EShopping.EShopping.controllers;

import EShopping.EShopping.dto.requests.CreateCommentRequest;
import EShopping.EShopping.dto.responses.GetAllCommentResponse;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.result.Result;
import EShopping.EShopping.service.CommentService;
import org.springframework.http.HttpStatus;
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
}
