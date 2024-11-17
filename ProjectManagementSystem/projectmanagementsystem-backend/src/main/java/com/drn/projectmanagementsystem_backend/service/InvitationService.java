package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.model.Invitation;
import jakarta.mail.MessagingException;

public interface InvitationService {

    public void sendInvitation(String email, Long projectId) throws MessagingException;

    public Invitation acceptInvitation(String token, Long userId);

    public String getTokenByUserMail(String userEmail);

    public void deleteToken(String token);

}
