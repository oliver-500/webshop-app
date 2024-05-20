package org.unibl.etf.ip.webshop.services.impl;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.webshop.exceptions.ConflictException;
import org.unibl.etf.ip.webshop.exceptions.IncorrectPasswordException;
import org.unibl.etf.ip.webshop.exceptions.NotFoundException;
import org.unibl.etf.ip.webshop.models.dto.UserDTO;
import org.unibl.etf.ip.webshop.models.entities.UserEntity;
import org.unibl.etf.ip.webshop.models.enums.Role;
import org.unibl.etf.ip.webshop.models.requests.SignUpRequest;
import org.unibl.etf.ip.webshop.models.requests.UpdateUserRequest;
import org.unibl.etf.ip.webshop.models.responses.TokenResponse;
import org.unibl.etf.ip.webshop.repositories.UserEntityRepository;
import org.unibl.etf.ip.webshop.security.JwtService;
import org.unibl.etf.ip.webshop.services.AuthService;
import org.unibl.etf.ip.webshop.services.SecurityService;
import org.unibl.etf.ip.webshop.services.UserService;
import org.unibl.etf.ip.webshop.util.ImageUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Value("${spring.mail.subject}")
    private String emailSubject;

    private final UserEntityRepository userRepo;
    private final ModelMapper mapper;

    private final JwtService jwtService;






    private final PasswordEncoder encoder;

    public UserServiceImpl(UserEntityRepository userRepo, ModelMapper mapper, JwtService jwtService, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.mapper = mapper;
        this.jwtService = jwtService;

        this.encoder = encoder;
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepo.findAll().stream().map(u -> mapper.map(u, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findAvatarByUsername(String username) throws NotFoundException {

        UserDTO u = findByUsername(username);
        UserDTO user = new UserDTO();
        user.setAvatar(u.getAvatar());
        return user;
    }

    @Override
    public UserDTO findById(Integer id) throws NotFoundException {
        UserEntity u = userRepo.findById(id).orElse(null);
        if(u == null) throw new NotFoundException();
        return mapper.map(u, UserDTO.class);
    }



    @Override
    public UserDTO insert(UserDTO user) {
        UserEntity u = mapper.map(user, UserEntity.class);
        u.setId(null);
        u.setActive(false);
        if(user.getAvatar() != null)
            u.setAvatar(ImageUtils.decodeBase64Image(user.getAvatar()));
        u.setType(Role.transactor);
        u.setPassword(encoder.encode(user.getPassword()));
        if(user.getAvatar() != null) u.setAvatar(ImageUtils.decodeBase64Image(user.getAvatar()));
        return mapper.map(userRepo.save(u), UserDTO.class);
    }


    @Override
    public UserDTO findByUsername(String username) throws NotFoundException {
        UserEntity u = userRepo.findByUsername(username);
        if(u == null) throw new NotFoundException();

        UserDTO us = mapper.map(u, UserDTO.class);
        if(u.getAvatar() != null)
        us.setAvatar(ImageUtils.codeToString(u.getAvatar()));
        return us;
    }


    

    @Override
    public boolean delete(Integer id) throws NotFoundException {
        UserEntity user = userRepo.findById(id).orElse(null);
        if (user == null) throw new NotFoundException();
        userRepo.deleteById(user.getId());
        return true;
    }



    @Override
    public UserDTO findByUserId(Integer idUser) throws NotFoundException{
        UserEntity t = userRepo.findById(idUser).orElseThrow(NotFoundException::new);

        return mapper.map(t, UserDTO.class);
    }

    @Override
    public UserDTO signUp(SignUpRequest request) throws ConflictException {
        if(userRepo.findByUsername(request.getUsername()) != null) throw new ConflictException();
        return insert(mapper.map(request, UserDTO.class));



    }

    @Override
    public UserDTO setActive(Integer id) throws NotFoundException{
        UserEntity t = userRepo.findById(id).orElseThrow(NotFoundException::new);
        t.setActive(true);
        return mapper.map(userRepo.saveAndFlush(t), UserDTO.class);

    }

    @Override
    public UserDTO findUsernameById(Integer id) throws NotFoundException {
        UserEntity t = userRepo.findById(id).orElseThrow(NotFoundException::new);
        UserDTO u = new UserDTO();
        u.setUsername(t.getUsername());
        return u;
    }

//    @Override
//    public String extractCode(Integer id) throws NotFoundException {
//        return auth.findById(id).orElseThrow(NotFoundException::new).getCode();
//        //in memory bazu
//    }
//
//    @Override
//    public String saveCode(Integer id, String toString) throws NotFoundException {
//        return null;
//    }

//    @Override
//    public String saveCode(Integer id, String code) throws NotFoundException{
//        UserEntity tr = userRepo.findById(id).orElseThrow(NotFoundException::new);
//        tr.setCode(code);
//        return userRepo.saveAndFlush(tr).getCode();
//    }



}
