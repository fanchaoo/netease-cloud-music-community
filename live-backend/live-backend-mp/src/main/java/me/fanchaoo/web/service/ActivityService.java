package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.ActivityDTO;
import me.fanchaoo.web.request.JoinedActivityRequest;
import me.fanchaoo.web.request.PageRequest;
import me.fanchaoo.web.request.QueryActivityRequest;
import me.fanchaoo.web.response.BaseResponse;

import java.util.List;

public interface ActivityService {

    BaseResponse<List<ActivityDTO>> queryActivity(QueryActivityRequest request);

    BaseResponse joinedActivity(JoinedActivityRequest request);

    BaseResponse<List<ActivityDTO>> queryJoinedActivity(PageRequest request);

}
