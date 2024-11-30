package com.drn.projectmanagementsystem_backend.controller;

import com.drn.projectmanagementsystem_backend.DTO.IssueDTO;
import com.drn.projectmanagementsystem_backend.exception.IssueNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.ProjectNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.Issue;
import com.drn.projectmanagementsystem_backend.model.Status;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.request.IssueRequest;
import com.drn.projectmanagementsystem_backend.response.AuthResponse;
import com.drn.projectmanagementsystem_backend.response.MessageResponse;
import com.drn.projectmanagementsystem_backend.service.IssueService;
import com.drn.projectmanagementsystem_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;
    private final UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws IssueNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws ProjectNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(issueService.getIssueByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issueRequest,
                                                @RequestHeader("Authorization") String jwt) throws IssueNotFoundException {
        User userToken = userService.findUserProfileByJwt(jwt);

        Issue createdIssue = issueService.createIssue(issueRequest, userToken);

        IssueDTO issueDTO = IssueDTO.builder()
                .id(createdIssue.getId())
                .description(createdIssue.getDescription())
                .dueDate(createdIssue.getDueDate())
                .priority(createdIssue.getPriority())
                .project(createdIssue.getProject())
                .projectId(createdIssue.getProjectId())
                .status(createdIssue.getStatus())
                .title(createdIssue.getTitle())
                .tags(createdIssue.getTags())
                .assignee(createdIssue.getAssignee())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(issueDTO);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,
                                                       @RequestHeader("Authorization") String jwt) throws UserNotFoundException, IssueNotFoundException, ProjectNotFoundException {
        User user = userService.findUserProfileByJwt(jwt);
        issueService.deleteIssue(issueId, user.getId());

        MessageResponse response = MessageResponse.builder()
                .message("Issue deleted.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{issueId}/assigne/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId, @PathVariable Long userId) throws UserNotFoundException, IssueNotFoundException {
        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(issue);
    }

    @PutMapping("{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable Long issueId,
                                                   @PathVariable String status) {
        Issue issue = issueService.updateIssueStatus(issueId, Status.valueOf(status.toUpperCase()));
        return ResponseEntity.status(HttpStatus.OK).body(issue);
    }



}
