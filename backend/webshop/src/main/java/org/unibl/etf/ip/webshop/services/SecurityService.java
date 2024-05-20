package org.unibl.etf.ip.webshop.services;


import org.unibl.etf.ip.webshop.exceptions.IncorrectPasswordException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.requests.UpdatePasswordRequest;
import org.unibl.etf.ip.webshop.models.requests.UpdateUserRequest;
import org.unibl.etf.ip.webshop.models.responses.TokenResponse;

public interface SecurityService {
    void checkPassword(UserDTO user, String password) throws IncorrectPasswordException;

    TokenResponse update(Integer id, UpdateUserRequest request) throws NotFoundException, IncorrectPasswordException;

    boolean checkUsername(Integer id) throws NotFoundException;

    TokenResponse changePassword(Integer id, UpdatePasswordRequest request) throws NotFoundException, IncorrectPasswordException;


}
