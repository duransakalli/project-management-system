package com.drn.projectmanagementsystem_backend.repository;

import com.drn.projectmanagementsystem_backend.model.Project;
import com.drn.projectmanagementsystem_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByOwner(User user);

    List<Project> findByNameContainingAndTeamContains(String partialName, User user);

    @Query("SELECT p FROM Project p join p.team t where t=:user")
    List<Project> findByTeam(@Param("user") User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);

}
