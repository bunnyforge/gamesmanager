package com.minecraft.k8s.mapper;

import com.minecraft.k8s.domain.entity.MinecraftServerEntity;
import com.minecraft.k8s.domain.model.MinecraftServer;
import org.springframework.stereotype.Component;

/**
 * 手动实现的 Mapper（因为涉及 JSON 转换）
 */
@Component
public class MinecraftServerMapper {

    public MinecraftServer entityToModel(MinecraftServerEntity entity) {
        if (entity == null) {
            return null;
        }

        MinecraftServer server = new MinecraftServer();
        server.setNamespace(entity.getNamespace());
        server.setName(entity.getName());
        server.setNodePort(entity.getNodePort());
        server.setClusterId(entity.getClusterId());
        server.setStatus(entity.getStatus());
        server.setK8sConfig(entity.getK8sConfigObject());
        server.setMinecraftConfig(entity.getMinecraftConfigObject());

        return server;
    }

    public MinecraftServerEntity modelToEntity(MinecraftServer model) {
        if (model == null) {
            return null;
        }

        MinecraftServerEntity entity = new MinecraftServerEntity();
        entity.setNamespace(model.getNamespace());
        entity.setName(model.getName());
        entity.setNodePort(model.getNodePort());
        entity.setClusterId(model.getClusterId());
        entity.setStatus(model.getStatus());
        entity.setK8sConfigObject(model.getK8sConfig());
        entity.setMinecraftConfigObject(model.getMinecraftConfig());

        return entity;
    }
}
