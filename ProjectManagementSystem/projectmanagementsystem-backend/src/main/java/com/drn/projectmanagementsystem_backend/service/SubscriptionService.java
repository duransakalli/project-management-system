package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.PlanType;
import com.drn.projectmanagementsystem_backend.model.Subscription;
import com.drn.projectmanagementsystem_backend.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);
    Subscription getUserSubscription(Long userId) throws UserNotFoundException;
    Subscription upgradeSubscription(Long userId, PlanType planType) throws UserNotFoundException;
    boolean isValid(Subscription subscription);

}
