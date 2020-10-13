package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.MomentDetailDTO;
import me.fanchaoo.web.dto.MomentDTO;
import me.fanchaoo.web.dto.MomentImageDTO;
import me.fanchaoo.web.request.*;
import me.fanchaoo.web.response.BaseResponse;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface MomentService {

    BaseResponse<List<MomentDTO>> querySquareMoment(QuerySquareMomentRequest request);

    List<MomentDTO> queryMoment(Long artistId, Long userId, String momentType, Long repostMomentId, List<Long> userIdList, Long activityId, List<Long> idList, Integer start, Integer pageSize);

    BaseResponse<List<MomentDTO>> queryFriendMoment(PageRequest request);

    BaseResponse<List<MomentDTO>> queryTopicMoment(QueryTopicMomentRequest request);

    BaseResponse<MomentDetailDTO> getMomentDetail(GetMomentDetailRequest request);

    void publishMoment(PublishMomentRequest request);

    void deleteMoment(DeleteMomentRequest request);

    BaseResponse<TreeMap<String, List<MomentImageDTO>>> querySquareImage(QuerySquareImageRequest request);

}
