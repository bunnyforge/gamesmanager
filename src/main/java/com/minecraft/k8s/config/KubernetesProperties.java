package com.minecraft.k8s.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kubernetes.kubeconfig")
public class KubernetesProperties {
    
    private String base64;  // Base64 编码的 kubeconfig 内容
}
