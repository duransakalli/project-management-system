package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.IssueNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.ProjectNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.Issue;
import com.drn.projectmanagementsystem_backend.model.Project;
import com.drn.projectmanagementsystem_backend.model.Status;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.repository.IssueRepository;
import com.drn.projectmanagementsystem_backend.request.IssueRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final ProjectService projectService;
    private final UserService userService;

    @Override
    public Issue getIssueById(Long issueId) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if(issue.isEmpty()) {
            throw new IssueNotFoundException("No issue found with issueId: " + issueId);
        }
        return issue.get();
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws ProjectNotFoundException {
        return issueRepository.findByProjectId(projectId);
    }

    @Override
    public Issue createIssue(IssueRequest issueRequest, User user) throws UserNotFoundException {
        Project project = projectService.getProjectById(issueRequest.getProjectId());

        Issue issue = Issue.builder()
                .title(issueRequest.getTitle())
                .description(issueRequest.getDescription())
                .status(issueRequest.getStatus())
                .projectId(issueRequest.getProjectId())
                .priority(issueRequest.getPriority())
                .dueDate(issueRequest.getDueDate())
                .project(project)
                .build();

        return issueRepository.save(issue);
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws IssueNotFoundException, UserNotFoundException {
        getIssueById(issueId);
        issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws IssueNotFoundException, UserNotFoundException {
        User user = userService.findUserById(userId);
        Issue issue = getIssueById(issueId);
        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue updateIssueStatus(Long issueId, Status status) {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
    }
}
