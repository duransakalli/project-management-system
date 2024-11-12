package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.InvalidProjectDataException;
import com.drn.projectmanagementsystem_backend.exception.ProjectNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.UserNotAuthorizedException;
import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.Chat;
import com.drn.projectmanagementsystem_backend.model.Project;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws InvalidProjectDataException {

        Project newProject = initializeProjectWithOwnerAndDetails(project, user);

        Project savedProject = projectRepository.save(newProject);

        Chat projectChat = initializeProjectChat(savedProject);
        savedProject.setChat(projectChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws ProjectNotFoundException {

        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);

        projects = filterByCategory(projects, category);
        projects = filterByTag(projects, tag);

        if (projects.isEmpty()) {
            throw new ProjectNotFoundException("No projects found for the specified criteria.");
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws ProjectNotFoundException {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()) {
            throw new ProjectNotFoundException("Project not found.");
        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws ProjectNotFoundException, UserNotAuthorizedException {
        getChatByProjectId(projectId);
//        userService.findUserById(userId);

        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updatedProject, Long projectId) throws ProjectNotFoundException, InvalidProjectDataException {
        Project project = getProjectById(projectId);

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setTags(updatedProject.getTags());

        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws ProjectNotFoundException, UserNotAuthorizedException {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (project.getTeam().contains(user)) {
            throw new UserNotAuthorizedException("User is already a member of the project.");
        }

        project.getChat().getUsers().add(user);
        project.getTeam().add(user);

        projectRepository.save(project);
    }

    @Override
    public void removeUserToProject(Long projectId, Long userId) throws ProjectNotFoundException, UserNotAuthorizedException {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (project.getTeam().contains(user)) {
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }

        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws ProjectNotFoundException {
        Project project = getProjectById(projectId);

        return project.getChat();
    }

    @Override
    public List<Project> searchProject(String keyword, User user) throws ProjectNotFoundException, UserNotFoundException {

        return projectRepository.findByNameContainingAndTeamContains(keyword, user);
    }

    private Project initializeProjectWithOwnerAndDetails(Project project, User owner) {
        Project newProject = Project.builder()
                .owner(project.getOwner())
                .tags(project.getTags())
                .name(project.getName())
                .category(project.getCategory())
                .description(project.getDescription())
                .build();

        newProject.getTeam().add(owner);

        return newProject;
    }

    private Chat initializeProjectChat(Project project) {
        Chat chat = Chat.builder()
                .project(project)
                .build();

        return chatService.createChat(chat);
    }

    private List<Project> filterByCategory(List<Project> projects, String category) {
        if (category == null) return projects;
        return projects.stream()
                .filter(project -> project.getCategory().equals(category))
                .toList();
    }

    private List<Project> filterByTag(List<Project> projects, String tag) {
        if (tag == null) return projects;
        return projects.stream()
                .filter(project -> project.getTags().contains(tag))
                .toList();
    }
}
