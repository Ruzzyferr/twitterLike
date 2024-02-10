package com.ruzzyfer.twitterlike.service;

import com.ruzzyfer.twitterlike.config.JwtService;
import com.ruzzyfer.twitterlike.dto.LoginDto;
import com.ruzzyfer.twitterlike.dto.LoginResponse;
import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.dto.UserSaveRequestDto;
import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.mapper.UserMapper;
import com.ruzzyfer.twitterlike.repository.UserRepository;
import com.ruzzyfer.twitterlike.util.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;



    public UserService(UserMapper userMapper, UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    public UserDto save(UserSaveRequestDto dto) throws MessagingException {

        User user = userMapper.toEntityFromSaveRequestDto(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user = userRepository.save(user);

        UserDto getDto = userMapper.toDto(user);

        emailService.sendEmail(
                dto.getEmail(),
                "Welcome to twitterLike!",
                "Twitter'a hos geldin " + dto.getUsername());

        return getDto;

    }

    public LoginResponse login( LoginDto dto) {

        LoginResponse loginBackDto = new LoginResponse();

        User gecici = userRepository.findByUsername(dto.getUsername()).orElseThrow();

        if (passwordEncoder.matches(dto.getPassword(),gecici.getPassword())) {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));


            loginBackDto.setId(gecici.getId());
            loginBackDto.setRole(gecici.getRole());
            loginBackDto.setToken(jwtService.generateToken(gecici));

            return loginBackDto;
        }
        throw  new RuntimeException("kontrol et");
    }

    public List<UserDto> listAllKullanici() {

        return userMapper.toDtoListFromEntity(userRepository.findAll());

    }

}
