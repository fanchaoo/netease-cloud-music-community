package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.UserDTO;
import me.fanchaoo.web.request.CancelLikeRequest;
import me.fanchaoo.web.request.LikeRequest;
import me.fanchaoo.web.request.QueryLikeUserRequest;
import me.fanchaoo.web.response.BaseResponse;

import java.util.List;

public interface LikeService {


    void like(LikeRequest request);

    void cancelLike(CancelLikeRequest request);

    BaseResponse<List<UserDTO>> queryLikeUser(QueryLikeUserRequest request);

}
