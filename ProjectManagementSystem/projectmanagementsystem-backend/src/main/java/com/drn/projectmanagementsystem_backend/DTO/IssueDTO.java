package com.drn.projectmanagementsystem_backend.DTO;

import com.drn.projectmanagementsystem_backend.model.Priority;
import com.drn.projectmanagementsystem_backend.model.Project;
import com.drn.projectmanagementsystem_backend.model.Status;
import com.drn.projectmanagementsystem_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private Long projectId;
    private Priority priority;
    private LocalDate dueDate;
    private List<String> tags = new ArrayList<>();
    private Project project;

    private User assignee;

}
