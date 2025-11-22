package com.minecraft.k8s.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.stereotype.Component;

import java.io.StringReader;

@Component
public class K8sClientFactory {

    public ApiClient createClient(String kubeconfigContent) {
        try {
            return ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new StringReader(kubeconfigContent))).build();
        } catch (ClassCastException e) {
            // 可能是 Base64 编码的，尝试解码
            try {
                String decoded = new String(java.util.Base64.getDecoder().decode(kubeconfigContent));
                return ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new StringReader(decoded))).build();
            } catch (Exception ex) {
                throw new RuntimeException(
                        "Failed to parse kubeconfig. Ensure it is valid YAML or Base64 encoded YAML.", ex);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create K8s client from kubeconfig", e);
        }
    }
}
