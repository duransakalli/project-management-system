package com.drn.projectmanagementsystem_backend.controller;

import com.drn.projectmanagementsystem_backend.model.PlanType;
import com.drn.projectmanagementsystem_backend.model.Subscription;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.service.SubscriptionService;
import com.drn.projectmanagementsystem_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Subscription> getUserSubscription(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.getUserSubscription(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(subscription);
    }

    @PatchMapping("/user")
    public ResponseEntity<Subscription> upgradeSubscription(
            @RequestParam PlanType planType,
            @RequestHeader("Authorization") String jwt) {

        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.upgradeSubscription(user.getId(), planType);
        return ResponseEntity.status(HttpStatus.OK).body(subscription);
    }


}
