package com.example.demo.service;

import com.example.demo.constants.AccountBlockReason;
import com.example.demo.constants.MailType;
import com.example.demo.dto.*;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.Mail;
import com.example.demo.repository.MailRepository;
import com.example.demo.repository.User;
import com.example.demo.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailServiceImpl emailService;
    private final MailRepository mailRepository;

    public UserService(UserRepository repository, UserMapper mapper, EmailServiceImpl emailService, MailRepository mailRepository) {
        this.userRepository = repository;
        this.userMapper = mapper;
        this.emailService = emailService;
        this.mailRepository = mailRepository;
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

    public UserGetBaseDto createUser(UserCreateDto body) throws MessagingException {
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

        EmailDetails emailToSendDetails = emailService.prepareCreateAccountConfirmEmailToSend(response.getEmail());

        Mail mailToSend = mailRepository.save(new Mail(entity, emailToSendDetails.getSubject(), emailToSendDetails.getBody(), MailType.ACCOUNT_CREATED));

        logger.info("createUser mail = {}, recipient = {} saved to the database.", mailToSend, mailToSend.getUser().getEmail());

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

        EmailDetails emailToSendDetails = emailService.prepareAccountConfirmationEmailToSend(response.getEmail());

        Mail mailToSend = mailRepository.save(new Mail(entity, emailToSendDetails.getSubject(), emailToSendDetails.getBody(), MailType.ACCOUNT_CONFIRMED));

        logger.info("changeUserActivationStatus mail = {}, recipient = {} saved to the database.", mailToSend, mailToSend.getUser().getEmail());

        return response;
    }

    public UserGetBaseDto loginUser(UserLoginDto body) throws Exception {
        logger.info("loginUser with body = {}", body);
        User entity = userRepository.findByEmail(body.getEmail()).orElseThrow(() -> {
            logger.info("loginUser User with email = {} does not exist.", body.getEmail());
            return new AuthenticationServiceException("Invalid email and/or password.");
        });

        if (entity.isAccountBlocked()) {
            logger.info("loginUser user = {} is blocked.", entity);

            throw new AuthenticationServiceException("Account is blocked.");
        }

        if (!Objects.equals(entity.getPassword(), body.getPassword())) {
            if (entity.getInvalidLoginAttempts() == 5) {
                entity.setAccountBlocked(true);
                entity.setBlockReason(AccountBlockReason.EXCEED_LOGIN_LIMIT);
                EmailDetails emailToSendDetails = emailService.prepareAccountBlockedEmailToSend(body.getEmail());

                Mail mailToSend = mailRepository.save(new Mail(entity, emailToSendDetails.getSubject(), emailToSendDetails.getBody(), MailType.ACCOUNT_BLOCKED_BY_LOGIN_LIMIT));
                logger.info("loginUser mail = {}, recipient = {} saved to the database.", mailToSend, body.getEmail());
            } else {
                entity.setInvalidLoginAttempts(entity.getInvalidLoginAttempts() + 1);
            }

            User updatedEntity = userRepository.save(entity);

            logger.info("loginUser invalid login try for user = {}.", updatedEntity);

            throw new AuthenticationServiceException("Invalid email and/or password.");
        }

        if (!entity.isActivated()) {
            logger.info("loginUser user = {} is not activated.", entity);

            throw new AuthenticationServiceException("Invalid email and/or password.");
        }

        entity.setInvalidLoginAttempts(0);

        entity.setLastLoggedIn(new Timestamp(new Date().getTime()));

        User loggedUser = userRepository.save(entity);

        logger.info("loginUser User = {} logged in.", loggedUser);

        return userMapper.userToUserGetBaseDto(loggedUser);
    }
}
