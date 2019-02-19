package me.foodbag.hello.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private LoadingCache<String, Integer> loadingCache;

  public LoginAttemptService() {
    super();
    loadingCache =
        CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(
                new CacheLoader<String, Integer>() {
                  @Override
                  public Integer load(final String s) throws Exception {
                    return 0;
                  }
                });
  }

  public void loginSucceeded(final String key) {
    loadingCache.invalidate(key);
  }

  void loginFailed(final String key) {
    int attempts;
    try {
      attempts = loadingCache.get(key);
    } catch (final ExecutionException e) {
      attempts = 0;
    }
    attempts++;
    loadingCache.put(key, attempts);
  }

  boolean isBlocked(final String key) {
    try {
        int maxAttempt = 10;
        return loadingCache.get(key) >= maxAttempt;
    } catch (final ExecutionException e) {
      return false;
    }
  }
}
