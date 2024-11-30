package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.CommentNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.IssueNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long issueId, Long userId, String content) throws UserNotFoundException, IssueNotFoundException;

    void deleteComment(Long commentId, Long userId) throws UserNotFoundException, CommentNotFoundException;

    List<Comment> findCommentByIssueId(Long issueId);

}
