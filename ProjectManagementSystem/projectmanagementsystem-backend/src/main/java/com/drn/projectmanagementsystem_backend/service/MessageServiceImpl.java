package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.ChatNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.ProjectNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.Chat;
import com.drn.projectmanagementsystem_backend.model.Message;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.repository.MessageRepository;
import com.drn.projectmanagementsystem_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws UserNotFoundException, ProjectNotFoundException, ChatNotFoundException {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + senderId));

        Chat chat =projectService.getChatByProjectId(projectId);

        Message message = Message.builder()
                .content(content)
                .sender(sender)
                .createdAt(LocalDateTime.now())
                .chat(chat)
                .build();

        chat.getMessages().add(message);
        return message;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long projectId) throws ProjectNotFoundException, ChatNotFoundException {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chat.getId());
    }
}
