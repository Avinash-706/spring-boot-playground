package com.learning.cms.service;

import com.learning.cms.dto.LoginRequestDTO;
import com.learning.cms.dto.RegisterRequestDTO;
import com.learning.cms.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO register(RegisterRequestDTO registerRequestDTO);
    UserResponseDTO login(LoginRequestDTO loginRequestDTO);
    UserResponseDTO getUserById(Long id);
}
