package com.drn.projectmanagementsystem_backend.request;

import com.drn.projectmanagementsystem_backend.model.Priority;
import com.drn.projectmanagementsystem_backend.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueRequest {

    private String title;
    private String description;
    private Status status;
    private Long projectId;
    private Priority priority;
    private LocalDate dueDate;

}
