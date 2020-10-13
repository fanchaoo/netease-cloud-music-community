package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.GetProfileDTO;
import me.fanchaoo.web.dto.UserDTO;
import me.fanchaoo.web.request.GetUserInfoRequest;
import me.fanchaoo.web.request.UpdateUserInfoRequest;
import me.fanchaoo.web.response.BaseResponse;

public interface UserService {

    BaseResponse<UserDTO> getUserInfo(GetUserInfoRequest request);

    BaseResponse updateUserInfo(UpdateUserInfoRequest request);
}
