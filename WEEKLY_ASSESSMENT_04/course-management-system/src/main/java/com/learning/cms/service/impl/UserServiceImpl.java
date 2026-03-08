package com.learning.cms.service.impl;

import com.learning.cms.dto.LoginRequestDTO;
import com.learning.cms.dto.RegisterRequestDTO;
import com.learning.cms.dto.UserResponseDTO;
import com.learning.cms.entity.User;
import com.learning.cms.exception.InvalidRequestException;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.repository.UserRepository;
import com.learning.cms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            throw new InvalidRequestException("Email already exists");
        }

        User user = modelMapper.map(registerRequestDTO, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getPassword().equals(loginRequestDTO.getPassword())) {
            throw new InvalidRequestException("Invalid credentials");
        }

        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
