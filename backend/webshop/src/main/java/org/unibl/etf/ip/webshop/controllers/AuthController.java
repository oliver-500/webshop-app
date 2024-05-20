package org.unibl.etf.ip.webshop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.exceptions.*;
import org.unibl.etf.ip.webshop.models.dto.LoginResponse;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.requests.ActivationRequest;
import org.unibl.etf.ip.webshop.models.requests.LoginRequest;
import org.unibl.etf.ip.webshop.models.requests.SignUpRequest;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.EmailService;
import org.unibl.etf.ip.webshop.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    private final EmailService emailService;

    public AuthController(UserService userService, AuthService authService, EmailService emailService) {
        this.userService = userService;
        this.authService = authService;
        this.emailService = emailService;
    }



    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) throws UnauthorizedException, WaitingAuthenticationException {
        LoginResponse lr = authService.login(req);
        return ResponseEntity.ok(lr);
    }

    @GetMapping
    @RequestMapping("/checkToken")
    public ResponseEntity<Void> checkToken()  {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @GetMapping
    @RequestMapping("/info")
    public ResponseEntity<UserDTO> getInfo(@RequestBody String token) throws NotFoundException {

        if(token.length() == 0) throw new NotFoundException();
        UserDTO us =authService.getInfo(token);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(us);
    }


    @PostMapping("/activate")
    public ResponseEntity<LoginResponse> activateAccount(@Valid @RequestBody ActivationRequest req) throws NotFoundException, IncorrectPINCodeException, UnauthorizedException, ActivationTimeoutException, IncorrectPasswordException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authService.activate(req));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody SignUpRequest request) throws ConflictException, WaitingAuthenticationException {

        try {
            userService.findByUsername(request.getUsername());
        } catch (NotFoundException ex) {
            UserDTO u = authService.signUp(request);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(u);
        }
        throw new ConflictException();





    }


}
