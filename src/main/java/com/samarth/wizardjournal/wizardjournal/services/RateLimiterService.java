package com.samarth.wizardjournal.wizardjournal.services;

import com.samarth.wizardjournal.wizardjournal.models.User;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    public class RateLimitExceededException extends RuntimeException {
        public RateLimitExceededException(String message) {
            super(message);
        }
    }

    private final ConcurrentHashMap<String, Bucket> ipBuckets = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Bucket> userBuckets = new ConcurrentHashMap<>();

    public Bucket resolveIpBucket(String url, String ip, Integer requestsPerMinute) {
        return ipBuckets.computeIfAbsent(url + ":" + ip, k ->
            Bucket4j.builder()
                .addLimit(Bandwidth.classic(requestsPerMinute, Refill.intervally(requestsPerMinute, Duration.ofMinutes(1))))
                .build()
        );
    }

    public Bucket resolveUserBucket(String url, Integer userId) {
        return userBuckets.computeIfAbsent(url + ": " + userId, k ->
            Bucket4j.builder()
                .addLimit(Bandwidth.classic(3, Refill.intervally(3, Duration.ofDays(1))))
                .build()
        );
    }

    public void limitPerIp(HttpServletRequest request, Integer requestsPerMinute) {
        String ip = request.getRemoteAddr();
        Bucket bucket = resolveIpBucket(request.getRequestURI(), ip, requestsPerMinute);
        if (!bucket.tryConsume(1)) {
            throw new RateLimitExceededException("Rate limit exceeded for IP: " + ip);
        }
    }

    public void limitPerIp(HttpServletRequest request) {
        limitPerIp(request, 2);
    }

    public void limitPerUser(HttpServletRequest request) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Bucket bucket = resolveUserBucket(request.getRequestURI(), user.getId());
        if (!bucket.tryConsume(1)) {
            throw new RateLimitExceededException("Rate limit exceeded for user: " + user.getId());
        }
    }
}
