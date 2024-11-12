package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.InvalidProjectDataException;
import com.drn.projectmanagementsystem_backend.exception.ProjectNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.UserNotAuthorizedException;
import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.Chat;
import com.drn.projectmanagementsystem_backend.model.Project;
import com.drn.projectmanagementsystem_backend.model.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, User user) throws InvalidProjectDataException;

    List<Project> getProjectByTeam(User user, String category, String tag) throws ProjectNotFoundException;

    Project getProjectById(Long projectId) throws ProjectNotFoundException;

    void deleteProject(Long projectId, Long userId) throws ProjectNotFoundException, UserNotAuthorizedException;

    Project updateProject(Project updatedProject, Long projectId) throws ProjectNotFoundException, InvalidProjectDataException;

    void addUserToProject(Long projectId, Long userId) throws ProjectNotFoundException, UserNotAuthorizedException;

    void removeUserToProject(Long projectId, Long userId) throws ProjectNotFoundException, UserNotAuthorizedException;

    Chat getChatByProjectId(Long projectId) throws ProjectNotFoundException;

    List<Project> searchProject(String keyword, User user) throws ProjectNotFoundException, UserNotFoundException;
}
