package com.project.saga;

import org.springframework.stereotype.Component;

@Component
public class MappingUtils {
    public LogDto mapToLogDto(Log entity){
        LogDto dto = new LogDto();
        dto.setId(entity.getId());
        dto.setCreated(entity.getCreated());
        return dto;
    }
    public Log mapToLogEntity(LogDto dto){
        Log entity = new Log();
        entity.setId(dto.getId());
        entity.setCreated(dto.getCreated());
        return entity;
    }
}
