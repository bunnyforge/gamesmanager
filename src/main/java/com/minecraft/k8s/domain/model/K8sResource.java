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
    private String version;
    
    // Modrinth 模组列表（普通服务器 + Mods 模式）
    private String modrinthProjects;
    
    // Modrinth 整合包配置（整合包模式）
    private String modrinthModpack;
    
    // 世界边界大小
    private Integer worldBorderSize;
    
    // 预生成半径
    private Integer pregenRadius;
    
    // RCON 启动命令
    private String rconStartupCommands;
    
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
        resource.setVersion(mcConfig.getVersion());
        
        // Modrinth 配置
        resource.setModrinthProjects(mcConfig.getModrinthProjects());
        resource.setModrinthModpack(mcConfig.getModrinthModpack());
        
        // 世界边界和预生成配置
        resource.setWorldBorderSize(mcConfig.getWorldBorderSize());
        resource.setPregenRadius(mcConfig.getPregenRadius());
        resource.setRconStartupCommands(buildRconStartupCommands(mcConfig));
        
        return resource;
    }
    
    /**
     * 构建 RCON 启动命令
     * 如果配置了世界边界，自动生成 Chunky 预生成和 ChunkyBorder 命令
     */
    private static String buildRconStartupCommands(MinecraftConfig mcConfig) {
        StringBuilder commands = new StringBuilder();
        
        Integer pregenRadius = mcConfig.getPregenRadius();
        Integer borderSize = mcConfig.getWorldBorderSize();
        
        // 预生成命令（Chunky）
        if (pregenRadius != null && pregenRadius > 0) {
            commands.append(String.format(
                "chunky world world,chunky center 0 0,chunky radius %d,chunky start",
                pregenRadius
            ));
        }
        
        // 世界边界命令（ChunkyBorder）
        if (borderSize != null && borderSize > 0) {
            if (commands.length() > 0) {
                commands.append(",");
            }
            commands.append(String.format("chunkyborder set circle %d", borderSize));
        }
        
        return commands.toString();
    }
}
