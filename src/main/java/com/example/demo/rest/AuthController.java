package com.example.demo.rest;

import com.example.demo.dto.UserActivateDto;
import com.example.demo.dto.UserCreateDto;
import com.example.demo.dto.UserGetBaseDto;
import com.example.demo.dto.UserLoginDto;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    public AuthController(UserService service) {
        this.userService = service;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserGetBaseDto> createUser(@Valid @RequestBody UserCreateDto body) {
        logger.info("Called createUser with body = {}.", body);
        UserGetBaseDto response = userService.createUser(body);

        logger.info("createUser response = {}.", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/register/confirm")
    public ResponseEntity<UserGetBaseDto> activateUser(@RequestBody UserActivateDto body) {
        logger.info("Called activateUser with body = {}.", body);
        UserGetBaseDto response = userService.changeUserActivationStatus(body);

        logger.info("createUser response = {}.", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserGetBaseDto> loginUser(@RequestBody UserLoginDto body) {
        logger.info("Called loginUser with body = {}.", body);
        UserGetBaseDto response = userService.loginUser(body);

        logger.info("loginUser response = {}.", response);
        return ResponseEntity.ok(response);
    }
}
