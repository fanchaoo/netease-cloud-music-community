package me.fanchaoo.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.dbo.NotificationDBO;
import me.fanchaoo.dbo.UserDBO;
import me.fanchaoo.enums.EnumFriendType;
import me.fanchaoo.enums.EnumNotificationType;
import me.fanchaoo.util.AssertUtils;
import me.fanchaoo.util.BeanCopyUtils;
import me.fanchaoo.util.PageUtils;
import me.fanchaoo.web.dao.mapper.*;
import me.fanchaoo.dbo.FollowRelationDBO;
import me.fanchaoo.web.dto.FriendCountDTO;
import me.fanchaoo.web.dto.LoginUserDTO;
import me.fanchaoo.enums.EnumFollowTargetType;
import me.fanchaoo.web.dto.UserDTO;
import me.fanchaoo.web.request.CancelFollowRequest;
import me.fanchaoo.web.request.FollowRequest;
import me.fanchaoo.web.request.QueryFriendRequest;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.FollowService;
import me.fanchaoo.util.UserContext;
import me.fanchaoo.web.service.NotificationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FollowServiceImpl implements FollowService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserQueryMapper userQueryMapper;
    @Resource
    private FollowRelationMapper followRelationMapper;
    @Resource
    private FollowRelationQueryMapper followRelationQueryMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private TagQueryMapper tagQueryMapper;
    @Autowired
    private NotificationService notificationService;


    @Override
    @Transactional
    public void follow(FollowRequest request) {

        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        EnumFollowTargetType targetType = request.getTargetType();
        Long targetId = request.getTargetId();

        if (Objects.equals(targetType, EnumFollowTargetType.USER)) {

            UserDBO userDBO = userQueryMapper.getById(artistId, targetId);
            AssertUtils.notNull(userDBO, "用户不存在：" + targetId);
            followRelationMapper.insertSelective(new FollowRelationDBO(userId, targetType.getValue(), targetId, artistId));
            userMapper.updateFollowCount(userId, artistId, 1);
            userMapper.updateFansCount(targetId, artistId, 1);

            // 发消息
            notificationService.sendNotification(NotificationDBO.builder()
                    .type(EnumNotificationType.FOLLOW_USER.getValue())
                    .receiveUserId(targetId)
                    .build());
        }

        if (Objects.equals(targetType, EnumFollowTargetType.TAG)) {
            tagMapper.updateFansCount(targetId, artistId, 1);
        }
    }

    @Override
    @Transactional
    public void cancelFollow(CancelFollowRequest request) {

        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        EnumFollowTargetType targetType = request.getTargetType();
        Long targetId = request.getTargetId();

        followRelationMapper.delete(new FollowRelationDBO(userId, targetType.getValue(), targetId, artistId));

        if (Objects.equals(targetType, EnumFollowTargetType.USER)) {
            userMapper.updateFollowCount(userId, artistId, -1);
            userMapper.updateFansCount(targetId, artistId, -1);
        }

        if (Objects.equals(targetType, EnumFollowTargetType.TAG)) {
            tagMapper.updateFansCount(targetId, artistId, -1);
        }
    }

    @Override
    public BaseResponse<List<UserDTO>> queryFriend(QueryFriendRequest request) {
        BaseResponse<List<UserDTO>> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        int start = PageUtils.getStart(pageNo, pageSize);
        EnumFriendType friendType = request.getFriendType();

        if (Objects.equals(friendType, EnumFriendType.FOLLOW)) {
            List<FollowRelationDBO> followRelationDBOList = followRelationQueryMapper.queryByUserTargetType(artistId, userId, EnumFollowTargetType.USER.getValue(), start, pageSize);
            if (CollectionUtils.isEmpty(followRelationDBOList)) {
                response.setBody(new ArrayList<>());
                return response;
            }
            List<Long> followUserIdList = followRelationDBOList.stream().map(FollowRelationDBO::getTargetId).collect(Collectors.toList());

            List<UserDBO> userDBOList = userQueryMapper.queryByUserIds(artistId, followUserIdList);
            List<UserDTO> userDTOList = BeanCopyUtils.copy(userDBOList, UserDTO.class);
            userDTOList.forEach(userDTO -> {
                userDTO.setHasFollowed(true);
            });
            response.setBody(userDTOList);
        }

        if (Objects.equals(friendType, EnumFriendType.FANS)) {
            List<FollowRelationDBO> followRelationDBOList = followRelationQueryMapper.queryByTargetTypeTargetId(artistId, EnumFollowTargetType.USER.getValue(), userId, start, pageSize);
            if (CollectionUtils.isEmpty(followRelationDBOList)) {
                response.setBody(new ArrayList<>());
                return response;
            }
            List<Long> fansUserIdList = followRelationDBOList.stream().map(FollowRelationDBO::getUserId).collect(Collectors.toList());

            List<FollowRelationDBO> followDBOList = followRelationQueryMapper.queryByUserTargetTypeTargetIds(artistId, userId, EnumFollowTargetType.USER.getValue(), fansUserIdList);
            Set<Long> followUserIdSet = followDBOList.stream().map(FollowRelationDBO::getTargetId).collect(Collectors.toSet());

            List<UserDBO> userDBOList = userQueryMapper.queryByUserIds(artistId, fansUserIdList);
            List<UserDTO> userDTOList = BeanCopyUtils.copy(userDBOList, UserDTO.class);
            userDTOList.forEach(userDTO -> {
                if (followUserIdSet.contains(userDTO.getId())) {
                    userDTO.setHasFollowed(true);
                } else {
                    userDTO.setHasFollowed(false);
                }
            });
            response.setBody(userDTOList);
        }

        return response;
    }

    @Override
    public BaseResponse<FriendCountDTO> countFriend() {
        BaseResponse<FriendCountDTO> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        int followCount = followRelationQueryMapper.countByUserTargetType(artistId, userId, EnumFollowTargetType.USER.getValue());
        int fansCount = followRelationQueryMapper.countByTargetTypeTargetId(artistId, EnumFollowTargetType.USER.getValue(), userId);

        FriendCountDTO dto = new FriendCountDTO();
        dto.setFollowCount(followCount);
        dto.setFansCount(fansCount);
        response.setBody(dto);
        return response;
    }
}
