package com.drn.projectmanagementsystem_backend.controller;

import com.drn.projectmanagementsystem_backend.model.Chat;
import com.drn.projectmanagementsystem_backend.model.Message;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.request.CreateMessageRequest;
import com.drn.projectmanagementsystem_backend.service.MessageService;
import com.drn.projectmanagementsystem_backend.service.ProjectService;
import com.drn.projectmanagementsystem_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest req) {
        User user = userService.findUserById(req.getSenderId());
        Chat chat = projectService.getChatByProjectId(req.getProjectId());
        Message message = messageService.sendMessage(req.getSenderId(), req.getProjectId(), req.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long projectId) {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }


}
