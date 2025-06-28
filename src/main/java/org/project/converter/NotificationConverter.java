package org.project.converter;

import org.project.config.ModelMapperConfig;
import org.project.entity.NotificationEntity;
import org.project.model.response.NotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationConverter {
    private ModelMapperConfig modelMapperConfig;

    @Autowired
    public void setModelMapperConfig(ModelMapperConfig modelMapperConfig) {
        this.modelMapperConfig = modelMapperConfig;
    }

    public NotificationResponse toResponse(NotificationEntity notificationEntity) {
        return modelMapperConfig.modelMapper().map(notificationEntity, NotificationResponse.class);
    }
}
