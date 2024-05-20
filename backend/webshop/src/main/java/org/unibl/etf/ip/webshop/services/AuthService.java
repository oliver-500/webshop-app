package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.exceptions.*;
import org.unibl.etf.ip.webshop.models.dto.LoginResponse;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.requests.ActivationRequest;
import org.unibl.etf.ip.webshop.models.requests.LoginRequest;
import org.unibl.etf.ip.webshop.models.requests.SignUpRequest;

public interface AuthService {


//    LoginResponse activate(String code) throws IncorrectPINCodeException, NotFoundException;


    LoginResponse activate(ActivationRequest req) throws IncorrectPINCodeException, NotFoundException, UnauthorizedException, ActivationTimeoutException, IncorrectPasswordException;

    String generateCode(UserDTO user) throws WaitingAuthenticationException;

    UserDTO signUp(SignUpRequest req) throws  WaitingAuthenticationException;


    String getCode(String username) throws NotFoundException, UnauthorizedException, ActivationTimeoutException;

    LoginResponse login(LoginRequest req) throws  UnauthorizedException, WaitingAuthenticationException;

    boolean verifyUserId(Integer id) throws NotFoundException;

    UserDTO getInfo(String token) throws NotFoundException;

    String extractUserUsername() throws NotFoundException;
}
