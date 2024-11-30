package com.drn.projectmanagementsystem_backend.repository;

import com.drn.projectmanagementsystem_backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByIssueId(Long issueId);

}
