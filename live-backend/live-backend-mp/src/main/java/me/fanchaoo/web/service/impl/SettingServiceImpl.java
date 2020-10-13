package me.fanchaoo.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.dbo.FeedbackDBO;
import me.fanchaoo.dbo.RegionCityDBO;
import me.fanchaoo.util.BeanCopyUtils;
import me.fanchaoo.util.UserContext;
import me.fanchaoo.web.dao.mapper.FeedbackMapper;
import me.fanchaoo.web.dao.mapper.RegionCityMapper;
import me.fanchaoo.web.dao.mapper.RegionCityQueryMapper;
import me.fanchaoo.web.dto.LoginUserDTO;
import me.fanchaoo.web.dto.RegionCityDTO;
import me.fanchaoo.web.request.AddFeedbackRequest;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.SettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class SettingServiceImpl implements SettingService {

    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private RegionCityQueryMapper regionCityQueryMapper;


    @Override
    public BaseResponse addFeedback(AddFeedbackRequest request) {
        BaseResponse response = new BaseResponse();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        String type = request.getType().getValue();
        String content = request.getContent();
        String contactInfo = request.getContactInfo();

        FeedbackDBO dbo = new FeedbackDBO();
        dbo.setType(type);
        dbo.setContactInfo(contactInfo);
        dbo.setContent(content);
        dbo.setArtistId(artistId);
        dbo.setUserId(userId);
        feedbackMapper.insertSelective(dbo);

        return response;
    }

    @Override
    public BaseResponse<List<RegionCityDTO>> queryCity() {
        BaseResponse<List<RegionCityDTO>> response = new BaseResponse<>();

        List<RegionCityDBO> dboList = regionCityQueryMapper.queryAll();
        List<RegionCityDTO> dtoList = BeanCopyUtils.copy(dboList, RegionCityDTO.class);
        response.setBody(dtoList);
        return response;
    }
}
