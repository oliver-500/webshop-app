package org.unibl.etf.ip.webshop.services.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.exceptions.UnauthorizedException;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.requests.LoginRequest;
import org.unibl.etf.ip.webshop.models.requests.LogsRequest;
import org.unibl.etf.ip.webshop.models.responses.LogMessage;
import org.unibl.etf.ip.webshop.models.responses.LogResponse;
import org.unibl.etf.ip.webshop.services.LogService;
import org.unibl.etf.ip.webshop.services.UserService;
import org.unibl.etf.ip.webshop.util.PasswordUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Service
public class LogServiceImpl implements LogService {

    @Value("${logging.file.name}")
    private String logFileName;
    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    public SseEmitter sseEmitter;

    private final UserService userService;

    private final HttpServletRequest request;
    private Object lockObject = new Object();

    public SseEmitter getEmitter(boolean isLogging) throws UnauthorizedException{
        if(!isLogging)
            if(!isAuthenticated()) throw new UnauthorizedException();
        if(sseEmitter == null) sseEmitter = new SseEmitter(-1L);

        sseEmitter.onTimeout(() -> {
            sseEmitter.complete();
            sseEmitter = null;
        });
        return sseEmitter;
    }

    private boolean isAuthenticated(){

        HttpSession session =  this.request.getSession();

        UserDTO admin = (UserDTO) session.getAttribute("user");

        if(admin == null) return false;
        else return true;
    }


    private int currentLine = -1;

    private void sendLogMessage(String message) {
        try {
            if(sseEmitter == null ){
                sseEmitter = new SseEmitter(-1L);
            }
            sseEmitter.send(SseEmitter.event().data(message));
        } catch (IOException e) {
            sseEmitter = new SseEmitter(-1L);
        }
    }



    public void logMessage(LogMessage message) {
        if(currentLine == -1 && logFileName != null){
            try {

                currentLine = countLines(logFileName);
            } catch (IOException e) {

            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            if(currentLine != -1){
                synchronized (lockObject) {
                    LOGGER.info(message.toString());
                }
            }
//            writeToFile(message.toString());


            String jsonMessage = objectMapper.writeValueAsString(message);
            sendLogMessage(jsonMessage);
        } catch (JsonProcessingException e) {

        }

    }

    @Override
    public List<LogResponse> findPrevious(LogsRequest req)  throws NotFoundException, UnauthorizedException {

        if(!isAuthenticated()) throw new UnauthorizedException();
        try {
            synchronized (lockObject){
                List<String> lines = readLinesAfterLineNumber(logFileName, req.getPosition(), req.getSize());
                return lines.stream().map(l -> new LogResponse(l)).collect(Collectors.toList());
            }


            // Process the lines as needed


        } catch (IOException e) {
            throw new NotFoundException();
        }

    }

    public LogServiceImpl(UserService userService, HttpServletRequest request){
        this.userService = userService;
        this.request = request;
    }

    @Override
    public void login(LoginRequest req) throws UnauthorizedException {
        try{
            UserDTO admin = userService.findByUsername(req.getUsername());
            if(!admin.getPassword().equals(PasswordUtil.generateHashSha256(req.getPassword()))){
                throw new UnauthorizedException();
            }

            HttpSession session =  this.request.getSession();
            admin.setPassword("");
            session.setAttribute("user", admin);

        }
        catch(NotFoundException e){
            throw new UnauthorizedException();
        }

    }

    private static List<String> readLinesAfterLineNumber(String filePath, int startLineNumber, int numberOfLines) throws IOException {
        Path path = Paths.get(filePath);

        // Read all lines from the file
        List<String> allLines = Files.readAllLines(path);
        Collections.reverse(allLines);
        if(startLineNumber > allLines.size()) return new ArrayList<String>();
        // Calculate the end index based on the start line number and number of lines to read
        int endLineNumber = Math.min(startLineNumber + numberOfLines, allLines.size());

        // Return the sublist of lines after the specified line number
        return allLines.subList(startLineNumber, endLineNumber);
    }



    private static int countLines(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            return lines;
        }
    }


}


