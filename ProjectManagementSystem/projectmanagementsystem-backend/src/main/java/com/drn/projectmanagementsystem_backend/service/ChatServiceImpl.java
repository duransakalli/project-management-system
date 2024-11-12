package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.model.Chat;
import com.drn.projectmanagementsystem_backend.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }

}
