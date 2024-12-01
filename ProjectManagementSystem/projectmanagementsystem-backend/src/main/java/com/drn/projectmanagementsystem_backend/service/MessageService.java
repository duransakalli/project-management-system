package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.ChatNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.ProjectNotFoundException;
import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.Message;

import java.util.List;

public interface MessageService {

    Message sendMessage(Long senderId, Long projectId, String content) throws UserNotFoundException, ProjectNotFoundException, ChatNotFoundException;

    List<Message> getMessagesByProjectId(Long projectId) throws ProjectNotFoundException, ChatNotFoundException;

}
