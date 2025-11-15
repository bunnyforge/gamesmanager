package com.minecraft.k8s.domain.model;

import com.minecraft.k8s.domain.valueobject.K8sConfig;
import com.minecraft.k8s.domain.valueobject.MinecraftConfig;
import lombok.Data;

/**
 * K8s 资源配置
 */
@Data
public class K8sResource {
    private String namespace;
    private String name;
    private Integer replicas;
    private Integer nodePort;
    private String serverType;
    private Boolean onlineMode;
    private Integer maxPlayers;
    private String maxMemory;
    private String memoryLimit;
    private String memoryRequest;
    private String cpuLimit;
    private String cpuRequest;
    private String storageSize;
    private String storageClassName;
    private String jvmOptions;
    
    public static K8sResource fromMinecraftServer(MinecraftServer server) {
        K8sResource resource = new K8sResource();
        resource.setNamespace(server.getNamespace());
        resource.setName(server.getName());
        resource.setNodePort(server.getNodePort());
        
        // 从 K8s 配置获取
        K8sConfig k8sConfig = server.getK8sConfig();
        resource.setReplicas(k8sConfig.getReplicas());
        resource.setMemoryLimit(k8sConfig.getMemoryLimit());
        resource.setMemoryRequest(k8sConfig.getMemoryRequest());
        resource.setCpuLimit(k8sConfig.getCpuLimit());
        resource.setCpuRequest(k8sConfig.getCpuRequest());
        resource.setStorageSize(k8sConfig.getStorageSize());
        resource.setStorageClassName(k8sConfig.getStorageClassName());
        
        // 从 Minecraft 配置获取
        MinecraftConfig mcConfig = server.getMinecraftConfig();
        resource.setServerType(mcConfig.getServerType());
        resource.setOnlineMode(mcConfig.getOnlineMode());
        resource.setMaxPlayers(mcConfig.getMaxPlayers());
        resource.setMaxMemory(mcConfig.getMaxMemory());
        resource.setJvmOptions(mcConfig.getJvmOptions());
        
        return resource;
    }
    

}
