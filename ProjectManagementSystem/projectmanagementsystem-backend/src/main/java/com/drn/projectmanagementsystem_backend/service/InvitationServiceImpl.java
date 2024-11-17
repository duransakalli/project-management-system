package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.InvitationNotFoundException;
import com.drn.projectmanagementsystem_backend.model.Invitation;
import com.drn.projectmanagementsystem_backend.repository.InvitationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    private final EmailService emailService;

    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {
        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = Invitation.builder()
                .email(email)
                .projectId(projectId)
                .token(invitationToken)
                .build();

        invitationRepository.save(invitation);

        String invitationLink = "http://localhost:5173/accept_invitation?token"+invitationToken;
        emailService.sendEmailWithToken(email, invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) {
        Invitation invitation = invitationRepository.findByToken(token);
        if(invitation == null) {
            throw new InvitationNotFoundException("Invitation not found with token: " + token);
        }
        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation invitation = invitationRepository.findByEmail(userEmail);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepository.findByToken(token);

        invitationRepository.delete(invitation);
    }
}
