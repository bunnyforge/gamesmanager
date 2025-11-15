package com.minecraft.k8s.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "更新 Minecraft 服务器请求")
public class UpdateServerRequest {
    @Schema(description = "Kubernetes 资源配置（可选更新）")
    private UpdateK8sConfigDTO k8sConfig;
    
    @Schema(description = "Minecraft 服务器配置（可选更新）")
    private UpdateMinecraftConfigDTO minecraftConfig;
    
    @Data
    @Schema(description = "Kubernetes 资源配置")
    public static class UpdateK8sConfigDTO {
        @Min(1)
        @Schema(description = "内存限制（单位：G）", example = "2")
        private Integer memoryLimit;
        
        @Min(1)
        @Schema(description = "CPU 限制（单位：核心）", example = "2")
        private Integer cpuLimit;
        
        @Min(1)
        @Schema(description = "存储大小（单位：G）", example = "2")
        private Integer storageSize;
    }
    
    @Data
    @Schema(description = "Minecraft 服务器配置")
    public static class UpdateMinecraftConfigDTO {
        @Min(1) @Max(10000)
        @Schema(description = "最大玩家数", example = "50", minimum = "1", maximum = "10000")
        private Integer maxPlayers;
        
        @Schema(description = "服务器类型", example = "PAPER", allowableValues = {"VANILLA", "PAPER", "FOLIA", "PURPUR"})
        private String serverType;
        
        @Schema(description = "是否开启正版验证", example = "true")
        private Boolean onlineMode;
        
        @Schema(description = "JVM 启动参数", example = "-XX:+UseG1GC")
        private String jvmOptions;
        
        @Schema(description = "Minecraft 版本", example = "1.20.4")
        private String version;
        
        @Schema(description = "游戏难度", example = "hard", allowableValues = {"peaceful", "easy", "normal", "hard"})
        private String difficulty;
        
        @Schema(description = "是否开启 PVP", example = "false")
        private Boolean pvp;
        
        @Schema(description = "视距", example = "12")
        private Integer viewDistance;
    }
}
