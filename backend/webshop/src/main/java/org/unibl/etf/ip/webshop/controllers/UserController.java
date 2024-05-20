package org.unibl.etf.ip.webshop.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.webshop.exceptions.IncorrectPasswordException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.requests.UpdatePasswordRequest;
import org.unibl.etf.ip.webshop.models.requests.UpdateUserRequest;
import org.unibl.etf.ip.webshop.models.responses.TokenResponse;
import org.unibl.etf.ip.webshop.services.SecurityService;
import org.unibl.etf.ip.webshop.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    final private UserService service;

    final private SecurityService securityService;

    public UserController(UserService service, SecurityService securityService) {
        this.service = service;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.insert(user));
    }

    @PutMapping("/{id}/changePassword")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<TokenResponse> changePassword(@PathVariable("id") Integer id,@Valid @RequestBody UpdatePasswordRequest request) throws NotFoundException, IncorrectPasswordException {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(securityService.changePassword(id, request));
    }

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> findOne(@PathVariable("id") Integer id) throws NotFoundException {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<TokenResponse> update(@PathVariable("id") Integer id, @RequestBody UpdateUserRequest request) throws IncorrectPasswordException, NotFoundException {


        return ResponseEntity.status(HttpStatus.ACCEPTED).body(securityService.update(id, request));


    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) throws NotFoundException{
        service.delete(id);
    }

    @GetMapping("/username/{id}")
    ResponseEntity<UserDTO> findUsername(@PathVariable("id") Integer id) throws NotFoundException {
        if(!securityService.checkUsername(id)) throw new NotFoundException();
        return ResponseEntity.ok(service.findUsernameById(id));
    }

    @GetMapping("/avatar/{username}")
    ResponseEntity<UserDTO> findAvatarOne(@PathVariable("username") String username) throws NotFoundException {

        return ResponseEntity.ok(service.findAvatarByUsername(username));
    }



}
