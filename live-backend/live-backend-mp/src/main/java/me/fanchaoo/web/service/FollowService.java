package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.FriendCountDTO;
import me.fanchaoo.web.dto.UserDTO;
import me.fanchaoo.web.request.CancelFollowRequest;
import me.fanchaoo.web.request.FollowRequest;
import me.fanchaoo.web.request.QueryFriendRequest;
import me.fanchaoo.web.response.BaseResponse;

import java.util.List;

public interface FollowService {

    void follow(FollowRequest request);

    void cancelFollow(CancelFollowRequest request);

    BaseResponse<List<UserDTO>> queryFriend(QueryFriendRequest request);

    BaseResponse<FriendCountDTO> countFriend();

}

