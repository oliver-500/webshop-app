package org.unibl.etf.ip.webshop.services.impl;

import jakarta.mail.internet.AddressException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.webshop.exceptions.*;
import org.unibl.etf.ip.webshop.models.dto.LoginResponse;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.requests.ActivationRequest;
import org.unibl.etf.ip.webshop.models.requests.LoginRequest;
import org.unibl.etf.ip.webshop.models.requests.SignUpRequest;
import org.unibl.etf.ip.webshop.models.responses.LogMessage;
import org.unibl.etf.ip.webshop.security.JwtService;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.EmailService;
import org.unibl.etf.ip.webshop.services.LogService;
import org.unibl.etf.ip.webshop.services.UserService;

import java.util.Random;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final ModelMapper mapper;
    private final UserService userService;

    @Value("${spring.mail.subject}")
    private String emailSubject;

    private final LogService logService;


    @Value("${authorization.token.header.name}")
    private String authorizationHeaderName;

    private final JwtService jwtService;

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    private final EmailService emailService;


    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(ModelMapper mapper, UserService userService, LogService logService, JwtService jwtService, HttpServletRequest request, HttpServletResponse response, EmailService emailService, AuthenticationManager authenticationManager) {
        this.mapper = mapper;
        this.userService = userService;
        this.logService = logService;
        this.jwtService = jwtService;
        this.request = request;
        this.response = response;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginResponse activate(ActivationRequest req) throws IncorrectPINCodeException, NotFoundException, UnauthorizedException, ActivationTimeoutException, IncorrectPasswordException {

        UserDTO u = null;
        try {
            u = userService.findByUsername(req.getUsername());
        } catch (NotFoundException ex) {
            logService.logMessage(new LogMessage(this, LogMessage.Error.INFO, ex.toString()));
            throw new UnauthorizedException();
        }


        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null && !user.getUsername().equals(req.getUsername())) {
            logService.logMessage(new LogMessage(this, LogMessage.Error.INFO, "Session invalid for " + req.getUsername() + "from: " + this.request.getRemoteAddr()));
            throw new UnauthorizedException();

        }

        String storedCode = getCode(req.getUsername());

        if (!storedCode.equals(req.getPin())) {
            logService.logMessage(new LogMessage(this, LogMessage.Error.INFO, "Failed account activation by " + req.getUsername()));
            throw new IncorrectPINCodeException();
        }

        String token = jwtService.generateToken(u);
        userService.setActive(u.getId());
        session.invalidate();
        return new LoginResponse(token);
    }

    @Override
    public String generateCode(UserDTO user) {
        HttpSession session = request.getSession();

        String code = Integer.toString((new Random()).nextInt(9000) + 1000);
        session.setAttribute("pin", code);
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(60 * 20); //20min
        System.out.println("kod 20min: " + code);

        return code;
    }

    @Override
    public UserDTO signUp(SignUpRequest req) throws WaitingAuthenticationException {

        UserDTO user = mapper.map(req, UserDTO.class);

        user.setActive(false);
        userService.insert(user);
        String code = generateCode(user);
        logService.logMessage(new LogMessage(this, LogMessage.Error.INFO, "Insert user:" + user.getUsername()));


//        try {
//
//            emailService.sendEmail(user.getEmail(), emailSubject, code);
//            throw new AddressException();
//        } catch (AddressException | MailException ae) {
//            logService.logMessage(new LogMessage(this, LogMessage.Error.ERROR, "Error sending email to " + user.getEmail()));
//
//        }


        return user;

    }

    @Override
    public String getCode(String username) throws NotFoundException, UnauthorizedException, ActivationTimeoutException {
        UserDTO u = userService.findByUsername(username);
        if (!u.getUsername().equals(username)) throw new UnauthorizedException();
        HttpSession session = request.getSession();
        UserDTO sessionUser = (UserDTO) session.getAttribute(("user"));
        if (sessionUser == null) throw new UnauthorizedException();

        if (!u.getUsername().equals(sessionUser.getUsername())) throw new UnauthorizedException();

        return (String) session.getAttribute("pin");
    }


    @Override
    public LoginResponse login(LoginRequest request) throws UnauthorizedException, WaitingAuthenticationException {
        LoginResponse response = new LoginResponse();

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDTO t = userService.findByUsername(request.getUsername());

            if (t.getActive()) {

                UserDTO user = (UserDTO) authenticate.getPrincipal();
                response.setToken(jwtService.generateToken(user));

            } else {

                String code = generateCode(t);
                this.response.setHeader("Errormessage", "Your account has not been activated. Please activate your account using code we sent to your email address.");

//                try {
//                    emailService.sendEmail(t.getEmail(), emailSubject, code);
//                    throw new AddressException();
//                } catch (AddressException | MailException ae) {
//                    logService.logMessage(new LogMessage(this, LogMessage.Error.ERROR, "Error sending email to " + t.getEmail()));
//                }
                int i = 343;
                if(i== 5) throw new WaitingAuthenticationException();
            }


        } catch (WaitingAuthenticationException ex) {
            logService.logMessage(new LogMessage(this, LogMessage.Error.INFO, "Waiting authentication  " + request.getUsername()));
            throw ex;
        } catch (AuthenticationException ex) {
            logService.logMessage(new LogMessage(this, LogMessage.Error.INFO, "Failed login from " + this.request.getRemoteAddr() + " " + request.getUsername()));
            throw new UnauthorizedException();
        } catch (Exception ex) {
            logService.logMessage(new LogMessage(this, LogMessage.Error.ERROR, ex.toString()));
            throw new UnauthorizedException();
        }
        return response;
    }

    public String extractUserUsername() throws NotFoundException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith(authorizationHeaderName)) {
            String jwtToken = authorizationHeader.substring(7);
            return jwtService.extractUsername(jwtToken);

        } else throw new NotFoundException();
    }

    public boolean verifyUserId(Integer id) throws NotFoundException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith(authorizationHeaderName)) {
            String jwtToken = authorizationHeader.substring(7); // Skip "Bearer "


            String username = jwtService.extractUsername(jwtToken);

            if (id.equals(userService.findByUsername(username).getId())) {

                return true;

            }
            // Now you have the JWT token, and you can process it as needed
        }
        return false;

    }

    @Override
    public UserDTO getInfo(String token) throws NotFoundException {
        String username = null;
        try {
            username = jwtService.extractUsername(token);
        } catch (Exception e) {
            throw new NotFoundException();
        }

        return userService.findByUsername(username);
    }

}
