package me.fanchaoo.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.dbo.MomentDBO;
import me.fanchaoo.dbo.NotificationDBO;
import me.fanchaoo.dbo.UserDBO;
import me.fanchaoo.enums.EnumNotificationType;
import me.fanchaoo.enums.EnumStatus;
import me.fanchaoo.util.AssertUtils;
import me.fanchaoo.util.PageUtils;
import me.fanchaoo.util.UserContext;
import me.fanchaoo.web.dao.mapper.*;
import me.fanchaoo.web.dto.LoginUserDTO;
import me.fanchaoo.web.dto.RepostUserDTO;
import me.fanchaoo.web.request.QueryRepostUserRequest;
import me.fanchaoo.web.request.RepostRequest;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.NotificationService;
import me.fanchaoo.web.service.RepostService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RepostServiceImpl implements RepostService {


    @Resource
    private MomentMapper momentMapper;
    @Resource
    private MomentQueryMapper momentQueryMapper;
    @Resource
    private MomentImageMapper momentImageMapper;
    @Resource
    private MomentImageQueryMapper momentImageQueryMapper;
    @Resource
    private MomentVideoMapper momentVideoMapper;
    @Resource
    private MomentVideoQueryMapper momentVideoQueryMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserQueryMapper userQueryMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    @Transactional
    public BaseResponse repost(RepostRequest request) {
        BaseResponse response = new BaseResponse();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long userId = loginUser.getUserId();
        Long artistId = loginUser.getArtistId();

        Long repostMomentId = request.getRepostMomentId();
        MomentDBO momentDBO = momentQueryMapper.getById(artistId, repostMomentId, EnumStatus.NORMAL.getValue());
        AssertUtils.notNull(momentDBO, "moment不存在：" + repostMomentId);

        MomentDBO newDBO = new MomentDBO();
        newDBO.setArtistId(artistId);
        newDBO.setUserId(userId);
        newDBO.setType(momentDBO.getType());
        newDBO.setTextContent(request.getTextContent());
        newDBO.setActivityId(momentDBO.getActivityId());
        newDBO.setRepostMomentId(repostMomentId);
        momentMapper.insertSelective(newDBO);

        // 增加转发数量
        momentMapper.updateRepostCount(artistId, repostMomentId, 1);

        // 发消息
        notificationService.sendNotification(NotificationDBO.builder()
                .type(EnumNotificationType.REPOST.getValue())
                .momentId(newDBO.getId())
                .receiveUserId(momentQueryMapper.getUserId(artistId, repostMomentId))
                .build());
        return response;
    }

    @Override
    public BaseResponse<List<RepostUserDTO>> queryRepostUser(QueryRepostUserRequest request) {
        BaseResponse<List<RepostUserDTO>> response = new BaseResponse<>();
        List<RepostUserDTO> dtoList = new ArrayList<>();
        response.setBody(dtoList);
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();

        // 查询转发动态
        Long repostMomentId = request.getRepostMomentId();
        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        int start = PageUtils.getStart(pageNo, pageSize);
        List<MomentDBO> momentDBOList = momentQueryMapper.queryByRepostMomentId(artistId, repostMomentId, start, pageSize);
        if (CollectionUtils.isEmpty(momentDBOList)) {
            return response;
        }

        // 用户信息
        List<Long> userIdList = new ArrayList<>(momentDBOList.stream().map(MomentDBO::getUserId).collect(Collectors.toSet()));
        List<UserDBO> userDBOList = userQueryMapper.queryByUserIds(artistId, userIdList);
        Map<Long, UserDBO> idToUser = userDBOList.stream().collect(Collectors.toMap(UserDBO::getId, Function.identity(), (key1, key2) -> key2));

        momentDBOList.forEach(moment -> {
            RepostUserDTO dto = new RepostUserDTO();
            dto.setRepostTime(moment.getCreateTime());
            dto.setTextContent(moment.getTextContent());

            UserDBO userDBO = idToUser.get(moment.getUserId());
            dto.setUserId(moment.getUserId());
            dto.setUserName(userDBO.getName());
            dto.setUserAvatarUrl(userDBO.getAvatarUrl());
            dtoList.add(dto);
        });

        return response;
    }


}
