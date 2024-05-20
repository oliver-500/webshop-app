package org.unibl.etf.ip.webshop.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.exceptions.IncorrectPasswordException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;
import org.unibl.etf.ip.webshop.models.requests.UpdatePasswordRequest;
import org.unibl.etf.ip.webshop.models.requests.UpdateUserRequest;
import org.unibl.etf.ip.webshop.models.responses.TokenResponse;
import org.unibl.etf.ip.webshop.repositories.UserEntityRepository;
import org.unibl.etf.ip.webshop.security.JwtService;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.SecurityService;
import org.unibl.etf.ip.webshop.util.ImageUtils;

@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {


    private final PasswordEncoder passwordEncoder;
    private final UserEntityRepository userRepo;

    private final PasswordEncoder encoder;
    private final ModelMapper mapper;

    private final AuthService authService;

    private final JwtService jwtService;

    public SecurityServiceImpl(PasswordEncoder passwordEncoder, UserEntityRepository userRepo, PasswordEncoder encoder, ModelMapper mapper, AuthService authService, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.mapper = mapper;
        this.authService = authService;

        this.jwtService = jwtService;
    }


    @Override
    public void checkPassword(UserDTO user, String password) throws IncorrectPasswordException {
        if(!passwordEncoder.matches(password, user.getPassword())) throw new IncorrectPasswordException();
    }

    @Override
    @Transactional
    public TokenResponse update(Integer id, UpdateUserRequest request) throws NotFoundException, IncorrectPasswordException {

        checkUsername(id);

        UserEntity existingUser = userRepo.findById(id).orElse(null);
        if (existingUser == null) throw new NotFoundException();
        checkPassword(mapper.map(existingUser, UserDTO.class), request.getPassword());
        mapper.map(request, existingUser);
        existingUser.setPassword(encoder.encode(existingUser.getPassword()));
        if(request.getAvatar().length() > 0){
            existingUser.setAvatar(ImageUtils.decodeBase64Image(request.getAvatar()));
        }
        UserDTO updatedUser = mapper.map(userRepo.saveAndFlush(existingUser), UserDTO.class);
        TokenResponse token = new TokenResponse();

        token.setToken(jwtService.generateToken(updatedUser));
        return token;
    }

    @Override
    public boolean checkUsername(Integer id) throws NotFoundException{
        UserEntity user = userRepo.findById(id).orElse(null);
        if(user == null) throw new NotFoundException();
        if(authService.verifyUserId(user.getId()) == false){
            throw new NotFoundException();
        }
        return true;
    }

    @Override
    public TokenResponse changePassword(Integer id, UpdatePasswordRequest request) throws NotFoundException, IncorrectPasswordException {

        if(!checkUsername(id))throw  new NotFoundException();

        UserEntity user = userRepo.findById(id).orElse(null);
        if(user == null) throw new NotFoundException();

        checkPassword(mapper.map(user, UserDTO.class), request.getOldPassword());

        user.setPassword(encoder.encode(request.getNewPassword()));
        userRepo.save(user);
        TokenResponse token = new TokenResponse();
        token.setToken(jwtService.generateToken(mapper.map(user, UserDTO.class)));
        return token;
    }



}
