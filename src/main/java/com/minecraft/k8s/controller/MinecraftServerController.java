package com.minecraft.k8s.controller;

import com.minecraft.k8s.domain.model.MinecraftServer;
import com.minecraft.k8s.dto.CreateServerRequest;
import com.minecraft.k8s.dto.UpdateServerRequest;
import com.minecraft.k8s.service.MinecraftServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/minecraft")
@RequiredArgsConstructor
@Tag(name = "Minecraft Server", description = "Minecraft 服务器管理 API")
public class MinecraftServerController {
    
    private final MinecraftServerService serverService;
    
    @PostMapping("/servers")
    @Operation(summary = "创建 Minecraft 服务器", description = "在 Kubernetes 集群中创建一个新的 Minecraft 服务器实例")
    public ResponseEntity<MinecraftServer> createServer(@Valid @RequestBody CreateServerRequest request) {
        MinecraftServer server = serverService.createServer(request);
        return ResponseEntity.ok(server);
    }
    
    @PutMapping("/servers/{namespace}")
    @Operation(summary = "更新 Minecraft 服务器", description = "根据 namespace 更新指定服务器的配置")
    public ResponseEntity<MinecraftServer> updateServer(
            @Parameter(description = "服务器命名空间", example = "mc-30001")
            @PathVariable String namespace,
            @Valid @RequestBody UpdateServerRequest request) {
        MinecraftServer server = serverService.updateServerByNamespace(namespace, request);
        return ResponseEntity.ok(server);
    }
    
    @DeleteMapping("/servers/{namespace}")
    @Operation(summary = "删除 Minecraft 服务器", description = "根据 namespace 从 Kubernetes 集群中删除指定的服务器")
    public ResponseEntity<Void> deleteServer(
            @Parameter(description = "服务器命名空间", example = "mc-30001")
            @PathVariable String namespace) {
        serverService.deleteServerByNamespace(namespace);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/servers/{namespace}")
    @Operation(summary = "获取服务器详情", description = "根据 namespace 获取指定 Minecraft 服务器的详细信息")
    public ResponseEntity<MinecraftServer> getServer(
            @Parameter(description = "服务器命名空间", example = "mc-30001")
            @PathVariable String namespace) {
        MinecraftServer server = serverService.getServerByNamespace(namespace);
        return ResponseEntity.ok(server);
    }
    
    @GetMapping("/servers")
    @Operation(summary = "获取服务器列表", description = "获取所有 Minecraft 服务器的列表")
    public ResponseEntity<List<MinecraftServer>> listServers() {
        List<MinecraftServer> servers = serverService.listServers();
        return ResponseEntity.ok(servers);
    }
}
