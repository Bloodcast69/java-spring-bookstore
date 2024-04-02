package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.userRepository = repository;
        this.userMapper = mapper;
    }

    public UserGetBaseDto getUserById(long id) {
        logger.info("getUserById with id = {}", id);
        User entity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " does not exist."));

        UserGetBaseDto response = userMapper.userToUserGetBaseDto(entity);

        logger.info("getUserById response = {}.", response);
        return response;
    }

    public UserGetFullDto getUserFullById(long id) {
        logger.info("getUserFullById with id = {}", id);
        User entity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " does not exist."));

        UserGetFullDto response = userMapper.userToUserGetFullDto(entity);

        logger.info("getUserFullById response = {}.", response);
        return response;
    }

    public UserGetBaseDto createUser(UserCreateDto body) {
        logger.info("createUser with body = {}", body);
        User entity;

        try {
            entity = userRepository.save(new User(body.getFirstName(), body.getLastName(), body.getEmail(), body.getPassword(), false));
        } catch (DataIntegrityViolationException ex) {
            // @TODO: Refactor to BadRequest to not cause security issue with checking existing accounts
            throw new DataIntegrityViolationException("User with email " + body.getEmail() + " already exists.");
        }

        UserGetBaseDto response = userMapper.userToUserGetBaseDto(entity);

        logger.info("createUser response = {}.", response);
        return response;
    }

    public UserGetBaseDto updateUser(UserUpdateDto body) {
        logger.info("updateUser with body = {}", body);
        User entity = userRepository.findById(body.getId()).orElseThrow(() -> new EntityNotFoundException("User with id " + body.getId() + " does not exist."));

        entity.setFirstName(body.getFirstName());
        entity.setLastName(body.getLastName());
        entity.setEmail(body.getEmail());
        entity.setPassword(body.getPassword());

        UserGetBaseDto response = userMapper.userToUserGetBaseDto(userRepository.save(entity));

        logger.info("updateUser response = {}.", response);
        return response;
    }

    public UserGetBaseDto deleteUser(long id) {
        logger.info("deleteUser with id = {}", id);
        User entity = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " does not exist."));

        userRepository.delete(entity);

        UserGetBaseDto response = userMapper.userToUserGetBaseDto(entity);
        logger.info("deleteUser response = {}.", response);

        return response;
    }

    public UserGetBaseDto changeUserActivationStatus(UserActivateDto body) {
        logger.info("changeUserActivationStatus with body = {}", body);

        User entity = userRepository.findById(body.getId()).orElseThrow(() -> {
            logger.info("User with id = {} does not exist.", body.getId());
            // @TODO: spring returns default 404 exception. Need custom exception class throw
            return new ResponseStatusException(HttpStatus.BAD_REQUEST);
        });

        entity.setActivated(body.isActivated());

        UserGetBaseDto response = userMapper.userToUserGetBaseDto(userRepository.save(entity));
        logger.info("changeUserActivationStatus response = {}.", response);

        return response;
    }

    public UserGetBaseDto loginUser(UserLoginDto body) {
        logger.info("loginUser with body = {}", body);
        User entity = userRepository.findByEmail(body.getEmail()).orElseThrow(() -> {
            logger.info("User with email = {} does not exist.", body.getEmail());
            return new AuthenticationServiceException("Invalid email and/or password.");
        });

        if (!Objects.equals(entity.getPassword(), body.getPassword()) || !entity.isActivated()) {
            throw new AuthenticationServiceException("Invalid email and/or password.");
        }

        return userMapper.userToUserGetBaseDto(entity);
    }
}
