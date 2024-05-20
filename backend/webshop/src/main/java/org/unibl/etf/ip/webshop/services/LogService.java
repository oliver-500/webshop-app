package org.unibl.etf.ip.webshop.services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.requests.LoginRequest;
import org.unibl.etf.ip.webshop.models.requests.LogsRequest;
import org.unibl.etf.ip.webshop.models.responses.LogMessage;
import org.unibl.etf.ip.webshop.models.responses.LogResponse;


import java.util.List;

public interface LogService {
     void logMessage(LogMessage message);

     public SseEmitter getEmitter(boolean isLogging) throws UnauthorizedException;

     List<LogResponse> findPrevious(LogsRequest req) throws NotFoundException, UnauthorizedException;

     void login(LoginRequest req) throws UnauthorizedException;

}
