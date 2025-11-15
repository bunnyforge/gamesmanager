package com.minecraft.k8s.service;

import com.minecraft.k8s.repository.MinecraftServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 端口分配器 - 自动分配可用端口
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PortAllocator {
    
    private final MinecraftServerRepository repository;
    
    @Value("${minecraft.port.start:31001}")
    private Integer startPort;
    
    @Value("${minecraft.port.end:32000}")
    private Integer endPort;
    
    public Integer allocatePort() {
        // 获取当前最大端口号
        Integer maxPort = repository.findMaxNodePort().orElse(startPort - 1);
        
        // 分配下一个端口
        Integer nextPort = maxPort + 1;
        
        // 检查是否超出范围
        if (nextPort > endPort) {
            // 从起始端口开始查找可用端口
            for (int port = startPort; port <= endPort; port++) {
                if (!repository.existsByNodePort(port)) {
                    log.info("Allocated port: {}", port);
                    return port;
                }
            }
            throw new RuntimeException("No available ports in range " + startPort + "-" + endPort);
        }
        
        log.info("Allocated port: {}", nextPort);
        return nextPort;
    }
    
    public String generateNamespace(Integer nodePort) {
        // 生成命名空间：minecraft{端口号}
        String namespace = "minecraft" + nodePort;
        log.info("Generated namespace: {}", namespace);
        return namespace;
    }
}
