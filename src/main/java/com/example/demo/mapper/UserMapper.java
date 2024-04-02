package com.example.demo.mapper;

import com.example.demo.dto.UserGetBaseDto;
import com.example.demo.dto.UserGetFullDto;
import com.example.demo.repository.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserGetBaseDto userToUserGetBaseDto(User user) {
        return new UserGetBaseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.isActivated());
    }

    public UserGetFullDto userToUserGetFullDto(User user) {
        return new UserGetFullDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.isActivated());
    }
}
