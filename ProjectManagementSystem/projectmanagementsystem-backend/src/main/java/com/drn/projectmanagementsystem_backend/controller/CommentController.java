package com.drn.projectmanagementsystem_backend.controller;

import com.drn.projectmanagementsystem_backend.exception.CommentNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.IssueNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.ProjectNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.Comment;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.request.CreateCommentRequest;
import com.drn.projectmanagementsystem_backend.response.MessageResponse;
import com.drn.projectmanagementsystem_backend.service.CommentService;
import com.drn.projectmanagementsystem_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CreateCommentRequest request,
            @RequestHeader("Authorization") String jwt) throws UserNotFoundException, IssueNotFoundException, ProjectNotFoundException {

        User user = userService.findUserProfileByJwt(jwt);
        Comment createdComment = commentService.createComment(request.getIssueId(), user.getId(), request.getContent());

        return ResponseEntity.status(HttpStatus.OK).body(createdComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId,
                                                         @RequestHeader("Authorization") String jwt) throws UserNotFoundException, CommentNotFoundException {
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());

        MessageResponse response = MessageResponse.builder()
                .message("Comment deleted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentsByIssueId(@PathVariable Long issueId) {
        List<Comment> comments = commentService.findCommentByIssueId(issueId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }


}
