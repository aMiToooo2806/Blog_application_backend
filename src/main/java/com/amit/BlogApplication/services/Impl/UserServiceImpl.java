package com.amit.BlogApplication.services.Impl;

import com.amit.BlogApplication.entities.Users;
import com.amit.BlogApplication.exceptations.ResourceNotFoundException;
import com.amit.BlogApplication.payloads.UserDto;
import com.amit.BlogApplication.repositories.UserRepo;
import com.amit.BlogApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        Users user = this.dtoToUser(userDto);
        Users savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);

    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        Users users = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users","id",userId));

        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());
        users.setAbout(userDto.getAbout());

        Users updatedUser = this.userRepo.save(users);
        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        Users users = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("Users","Id",userId));

        return this.userToDto(users);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<Users> usersList = this.userRepo.findAll();
        List<UserDto> userDtos = usersList.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        Users user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Users", "Id", userId));

        this.userRepo.delete(user);

    }

    private Users dtoToUser(UserDto userDto)
    {
        Users users = new Users();
        users.setId(userDto.getId());
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());
        users.setAbout(userDto.getAbout());
        return users;
    }
    public UserDto userToDto(Users users)
    {
        UserDto userDto = new UserDto();
        userDto.setId(users.getId());
        userDto.setName(users.getName());
        userDto.setEmail(users.getEmail());
        userDto.setPassword(users.getPassword());
        userDto.setAbout(users.getAbout());
        return userDto;
    }
}
