package com.drn.projectmanagementsystem_backend.repository;

import com.drn.projectmanagementsystem_backend.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Invitation findByToken(String token);

    Invitation findByEmail(String userEmail);

}
