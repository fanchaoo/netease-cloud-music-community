package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.RegionCityDTO;
import me.fanchaoo.web.dto.RepostUserDTO;
import me.fanchaoo.web.request.AddFeedbackRequest;
import me.fanchaoo.web.request.QueryRepostUserRequest;
import me.fanchaoo.web.request.RepostRequest;
import me.fanchaoo.web.response.BaseResponse;

import java.util.List;

public interface SettingService {

    BaseResponse addFeedback(AddFeedbackRequest request);

    BaseResponse<List<RegionCityDTO>> queryCity();

}
