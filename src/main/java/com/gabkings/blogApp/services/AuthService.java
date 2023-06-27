package com.gabkings.blogApp.services;

import com.gabkings.blogApp.payload.LoginDto;
import com.gabkings.blogApp.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
