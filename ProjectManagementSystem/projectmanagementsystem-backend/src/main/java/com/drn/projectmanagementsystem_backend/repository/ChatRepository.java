package com.drn.projectmanagementsystem_backend.repository;

import com.drn.projectmanagementsystem_backend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
