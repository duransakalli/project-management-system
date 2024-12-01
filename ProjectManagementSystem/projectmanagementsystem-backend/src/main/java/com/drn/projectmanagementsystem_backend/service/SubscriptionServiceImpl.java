package com.drn.projectmanagementsystem_backend.service;

import com.drn.projectmanagementsystem_backend.exception.UserNotFoundException;
import com.drn.projectmanagementsystem_backend.model.PlanType;
import com.drn.projectmanagementsystem_backend.model.Subscription;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserService userService;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = Subscription.builder()
                .user(user)
                .subscriptionStartDate(LocalDate.now())
                .subscriptionEndDate(LocalDate.now().plusMonths(12))
                .isValid(true)
                .planType(PlanType.FREE)
                .build();
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) throws UserNotFoundException {
        Subscription subscription =  subscriptionRepository.findByUserId(userId);
        if (subscription == null) {
            throw new UserNotFoundException("No subscription found for user with id: " + userId);
        }

        if (!isValid(subscription)) {
            resetToFreePlan(subscription);
        }

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) throws UserNotFoundException {

        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if (subscription == null) {
            throw new UserNotFoundException("Subscription not found for user with id: " + userId);
        }
        subscription.setPlanType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());

        LocalDate endDate = calculateEndDate(planType);
        subscription.setSubscriptionEndDate(endDate);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if(subscription.getPlanType().equals(PlanType.FREE)){
            return true;
        }

        LocalDate subscriptionEndDate  = subscription.getSubscriptionEndDate();
        LocalDate today = LocalDate.now();

        return !subscriptionEndDate.isBefore(today);
    }

    private LocalDate calculateEndDate(PlanType planType) {
        return planType == PlanType.ANNUALLY
                ? LocalDate.now().plusMonths(12) // Annual subscription
                : LocalDate.now().plusMonths(1); // Monthly subscription
    }

    private void resetToFreePlan(Subscription subscription) {
        subscription.setPlanType(PlanType.FREE);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12)); // Default FREE plan duration
    }
}
