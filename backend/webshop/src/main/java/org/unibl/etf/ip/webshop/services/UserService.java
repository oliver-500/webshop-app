package org.unibl.etf.ip.webshop.services;

import org.unibl.etf.ip.webshop.exceptions.ConflictException;
import org.unibl.etf.ip.webshop.exceptions.IncorrectPasswordException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.requests.SignUpRequest;
import org.unibl.etf.ip.webshop.models.requests.UpdateUserRequest;
import org.unibl.etf.ip.webshop.models.responses.TokenResponse;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();

    UserDTO findAvatarByUsername(String username) throws NotFoundException;

    UserDTO findById(Integer id) throws NotFoundException;



    UserDTO insert(UserDTO trader);

    boolean delete(Integer id) throws NotFoundException;


    UserDTO findByUsername(String username) throws NotFoundException;

    UserDTO findByUserId(Integer idUser) throws NotFoundException;

    UserDTO signUp(SignUpRequest request) throws ConflictException;

    UserDTO setActive(Integer id) throws NotFoundException;

    UserDTO findUsernameById(Integer id) throws NotFoundException;

//    String extractCode(Integer id) throws NotFoundException;
//
//    String saveCode(Integer id, String toString) throws NotFoundException;

}
