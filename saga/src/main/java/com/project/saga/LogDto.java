package com.project.saga;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class LogDto {
    private Long id;
    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
