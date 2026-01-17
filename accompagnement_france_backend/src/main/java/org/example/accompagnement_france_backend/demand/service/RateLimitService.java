package org.example.accompagnement_france_backend.demand.service;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class RateLimitService {
    // Key: IP Address (String), Value: AtomicInteger (Demand count)
    private final Cache<String, AtomicInteger> requestCounts = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS) // 3/hour, so the window is 1 hour
            .build();

    private static final int MAX_REQUESTS = 3;

    public boolean isRateLimited(String ipAddress) {
        try {
            // Get or create the counter for the IP. Initial value is 0.
            AtomicInteger count = requestCounts.get(ipAddress, () -> new AtomicInteger(0));

            int currentCount = count.incrementAndGet();

            if (currentCount > MAX_REQUESTS) {
                // Log and reject
                log.warn("Rate limit exceeded for IP: {}", ipAddress);
                return true;
            }
            return false;
        } catch (ExecutionException e) {
            // Should not happen in this simplified case
            return false;
        }
    }
}
