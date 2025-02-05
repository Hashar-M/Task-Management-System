package com.hashar.Task_Management_System.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class BruteForceProtectionService {
    private final int MAX_ATTEMPT = 5;
    private final long LOCK_TIME_DURATION = TimeUnit.MINUTES.toMillis(15);
    private Map<String, Integer> attemptsCache = new HashMap<>();  //stores the number of attempts of each user
    private Map<String, Long> lockTimeCache = new HashMap<>();  //stores the time when the user was blocked

    public void loginSucceeded(String key) {
        attemptsCache.remove(key);
        lockTimeCache.remove(key);
    }

    public void loginFailed(String key) {
        int attempts = attemptsCache.getOrDefault(key, 0);
        attempts++;
        attemptsCache.put(key, attempts);
        if (attempts >= MAX_ATTEMPT) {
            lockTimeCache.put(key, System.currentTimeMillis());
        }
    }

    public boolean isBlocked(String key) {
        if (!lockTimeCache.containsKey(key)) {
            return false;
        }
        long lockTime = lockTimeCache.get(key);
        if (System.currentTimeMillis() - lockTime > LOCK_TIME_DURATION) {
            lockTimeCache.remove(key);
            attemptsCache.remove(key);
            return false;
        }
        return true;
    }
}
