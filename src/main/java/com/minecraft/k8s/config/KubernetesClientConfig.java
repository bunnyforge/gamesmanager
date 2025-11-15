package com.minecraft.k8s.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KubernetesClientConfig {
    
    private final KubernetesProperties properties;
    
    @Bean
    public ApiClient kubernetesApiClient() throws Exception {
        log.info("Initializing Kubernetes client from kubeconfig");
        
        if (properties.getBase64() == null || properties.getBase64().isEmpty()) {
            throw new IllegalStateException("kubeconfig.base64 must be configured");
        }
        
        log.info("Loading kubeconfig from base64 string");
        String decoded = new String(Base64.getDecoder().decode(properties.getBase64()), StandardCharsets.UTF_8);
        KubeConfig kubeConfig = KubeConfig.loadKubeConfig(new StringReader(decoded));
        
        // 使用原生方式构建客户端
        log.info("Building Kubernetes client from kubeconfig");
        ApiClient client = ClientBuilder.kubeconfig(kubeConfig).build();
        log.info("Kubernetes client built successfully with server: {}", kubeConfig.getServer());
        
        // 设置为默认客户端
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(client);
        
        log.info("Kubernetes client initialized successfully");
        return client;
    }
}
