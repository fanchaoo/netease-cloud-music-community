package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.LoginUserDTO;
import me.fanchaoo.web.request.LoginRequest;

public interface LoginService {
    LoginUserDTO login(LoginRequest request) throws Exception;
}
