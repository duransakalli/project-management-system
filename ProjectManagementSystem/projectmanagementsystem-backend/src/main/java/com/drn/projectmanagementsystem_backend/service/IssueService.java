package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.IssueNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.ProjectNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.Issue;
import com.drn.projectmanagementsystem_backend.model.Status;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssueById(Long issueId) throws IssueNotFoundException;

    List<Issue> getIssueByProjectId(Long projectId) throws ProjectNotFoundException;

    Issue createIssue(IssueRequest issue, User user) throws UserNotFoundException;

    void deleteIssue(Long issueId, Long userId) throws IssueNotFoundException, UserNotFoundException;

    Issue addUserToIssue(Long issueId, Long userId) throws IssueNotFoundException, UserNotFoundException;

    Issue updateIssueStatus(Long issueId, Status status);

}
