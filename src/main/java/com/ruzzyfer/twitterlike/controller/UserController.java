package com.ruzzyfer.twitterlike.controller;

import com.ruzzyfer.twitterlike.config.JwtService;
import com.ruzzyfer.twitterlike.dto.LoginDto;
import com.ruzzyfer.twitterlike.dto.LoginResponse;
import com.ruzzyfer.twitterlike.dto.UserDto;
import com.ruzzyfer.twitterlike.dto.UserSaveRequestDto;
import com.ruzzyfer.twitterlike.entity.User;
import com.ruzzyfer.twitterlike.repository.UserRepository;
import com.ruzzyfer.twitterlike.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<UserDto> save(@RequestBody UserSaveRequestDto dto) throws MessagingException {

        UserDto userDto = userService.save(dto);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto dto) {

        User gecici = userRepository.findByUsername(dto.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        LoginResponse loginBackDto = userService.login(dto);


        return new ResponseEntity<>(loginBackDto, HttpStatus.OK);
    }




}
