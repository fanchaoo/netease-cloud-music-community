package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.RepostUserDTO;
import me.fanchaoo.web.request.QueryRepostUserRequest;
import me.fanchaoo.web.request.RepostRequest;
import me.fanchaoo.web.response.BaseResponse;

import java.util.List;

public interface RepostService {

    BaseResponse repost(RepostRequest request);

    BaseResponse<List<RepostUserDTO>> queryRepostUser(QueryRepostUserRequest request);
}
