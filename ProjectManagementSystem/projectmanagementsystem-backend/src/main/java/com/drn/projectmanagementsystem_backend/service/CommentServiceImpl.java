package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.CommentNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.IssueNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.UserNotAuthorizedException;
import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.Comment;
import com.drn.projectmanagementsystem_backend.model.Issue;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.repository.CommentRepository;
import com.drn.projectmanagementsystem_backend.repository.IssueRepository;
import com.drn.projectmanagementsystem_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws UserNotFoundException, IssueNotFoundException {

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new IssueNotFoundException("Issue not found with id: " + issueId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Comment comment = Comment.builder()
                .issue(issue)
                .user(user)
                .createdAt(LocalDateTime.now())
                .content(content)
                .build();

        Comment savedComment = commentRepository.save(comment);
        issue.getComments().add(savedComment);

        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws UserNotFoundException, CommentNotFoundException {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IssueNotFoundException("Comment not found with id: " + commentId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommentNotFoundException("User not found with id: " + userId));

        if (!comment.getUser().equals(user)) {
            throw new UserNotAuthorizedException("User does not have permission to delete this comment!");
        }

        commentRepository.delete(comment);

    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepository.findByIssueId(issueId);
    }
}
