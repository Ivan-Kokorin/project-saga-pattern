package com.project.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/log")
public class LogController {
    private final static Logger LOG = LoggerFactory.getLogger(LogController.class);
    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<Log> log() {
        Log log = logService.save(new Log());
        LOG.info("saved new log id: " + log.getId() + ", time:" + log.getCreated());
        return ResponseEntity.ok(log);
    }
}
