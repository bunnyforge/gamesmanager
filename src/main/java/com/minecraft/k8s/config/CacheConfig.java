package com.minecraft.k8s.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.CacheManager;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.concurrent.TimeUnit;

/**
 * 缓存配置
 * 用于服务器指标缓存，使用 refreshAfterWrite 实现自动刷新
 * 
 * 刷新策略：
 * - refreshAfterWrite: 20秒后，下次访问时异步刷新（返回旧值，后台更新）
 * - expireAfterWrite: 60秒后强制过期（防止长时间不访问导致数据过旧）
 */
@Configuration
@EnableCaching
public class CacheConfig {

    // 刷新时间：20秒后触发异步刷新
    private static final long REFRESH_SECONDS = 20;
    // 过期时间：60秒后强制过期
    private static final long EXPIRE_SECONDS = 60;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("serverMetrics");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .refreshAfterWrite(REFRESH_SECONDS, TimeUnit.SECONDS)
                .expireAfterWrite(EXPIRE_SECONDS, TimeUnit.SECONDS)
                .maximumSize(1000));
        return cacheManager;
    }
}
