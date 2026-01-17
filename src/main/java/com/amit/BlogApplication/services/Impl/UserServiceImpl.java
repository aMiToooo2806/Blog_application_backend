package com.amit.BlogApplication.services.Impl;

import com.amit.BlogApplication.entities.Users;
import com.amit.BlogApplication.exceptations.ResourceNotFoundException;
import com.amit.BlogApplication.payloads.UserDto;
import com.amit.BlogApplication.repositories.RoleRepo;
import com.amit.BlogApplication.repositories.UserRepo;
import com.amit.BlogApplication.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.amit.BlogApplication.entities.Role;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String ROLE_USER = "ROLE_USER";

    @Override
    public UserDto createUser(UserDto userDto) {
        Users user = this.dtoToUser(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // assign default role to every created user
        Role roleUser = this.roleRepo.findByName(ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", ROLE_USER));

        user.getRoles().add(roleUser);

        Users savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);

    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        Users users = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Users","id",userId));

        users.setUsername(userDto.getUsername());
        users.setEmail(userDto.getEmail());
        users.setPassword(passwordEncoder.encode(userDto.getPassword()));
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

        return modelMapper.map(userDto,Users.class);
    }

    public UserDto userToDto(Users users) {
        UserDto dto = modelMapper.map(users, UserDto.class);

        // ✅ manual mapping for roles
        Set<String> roles = users.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        dto.setRoles(roles);

        return dto;
    }
}
