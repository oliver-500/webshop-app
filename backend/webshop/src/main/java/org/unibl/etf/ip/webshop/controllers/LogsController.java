package org.unibl.etf.ip.webshop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.requests.LoginRequest;
import org.unibl.etf.ip.webshop.models.requests.LogsRequest;
import org.unibl.etf.ip.webshop.models.responses.LogMessage;
import org.unibl.etf.ip.webshop.models.responses.LogResponse;
import org.unibl.etf.ip.webshop.services.LogService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
public class LogsController {




    private final LogService logService;

    public LogsController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeToLogs() throws UnauthorizedException{
        SseEmitter sseEmitter =  logService.getEmitter(false);

        return sseEmitter;
    }

    @PostMapping("/history")
    public ResponseEntity<List<LogResponse>> getHistory(@Valid  @RequestBody LogsRequest request) throws NotFoundException, UnauthorizedException {
        
        return ResponseEntity.ok(logService.findPrevious(request));
    }

    @PostMapping("/authenticate")
    public void authenticateAdmin(@Valid  @RequestBody LoginRequest request) throws UnauthorizedException{
        logService.login(request);
    }
}
