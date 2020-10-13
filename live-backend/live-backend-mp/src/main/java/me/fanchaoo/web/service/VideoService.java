package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.MomentImageDTO;
import me.fanchaoo.web.request.QueryVideoCoverRequest;
import me.fanchaoo.web.response.BaseResponse;

import java.util.List;

public interface VideoService {


    BaseResponse<List<MomentImageDTO>> queryVideoCover(QueryVideoCoverRequest request);

}
