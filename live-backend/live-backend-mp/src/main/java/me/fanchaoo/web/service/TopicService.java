package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.RegionCityDTO;
import me.fanchaoo.web.dto.TopicDTO;
import me.fanchaoo.web.request.AddFeedbackRequest;
import me.fanchaoo.web.request.GetTopicDetailRequest;
import me.fanchaoo.web.request.QueryTopicRequest;
import me.fanchaoo.web.request.UpdateTopicCoverUrlRequest;
import me.fanchaoo.web.response.BaseResponse;

import java.util.List;

public interface TopicService {

    BaseResponse<List<TopicDTO>> queryTopic(QueryTopicRequest request);

    BaseResponse<TopicDTO> getTopicDetail(GetTopicDetailRequest request);

    BaseResponse updateTopicCoverUrl(UpdateTopicCoverUrlRequest request);
}
