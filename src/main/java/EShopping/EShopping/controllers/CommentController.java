package EShopping.EShopping.controllers;

import EShopping.EShopping.dto.responses.GetAllCommentResponse;
import EShopping.EShopping.result.DataResult;
import EShopping.EShopping.service.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
