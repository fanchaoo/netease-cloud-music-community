package me.fanchaoo.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.dbo.CommentDBO;
import me.fanchaoo.dbo.NotificationDBO;
import me.fanchaoo.dbo.UserDBO;
import me.fanchaoo.enums.EnumBoolean;
import me.fanchaoo.enums.EnumNotificationCategory;
import me.fanchaoo.enums.EnumNotificationType;
import me.fanchaoo.util.BeanCopyUtils;
import me.fanchaoo.util.PageUtils;
import me.fanchaoo.util.UserContext;
import me.fanchaoo.web.dao.mapper.*;
import me.fanchaoo.web.dto.CommentNotificationDTO;
import me.fanchaoo.web.dto.LoginUserDTO;
import me.fanchaoo.web.dto.MomentDTO;
import me.fanchaoo.web.dto.AtNotificationDTO;
import me.fanchaoo.web.request.ClearNotificationCountRequest;
import me.fanchaoo.web.request.PageRequest;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.MomentService;
import me.fanchaoo.web.service.NotificationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Resource
    private NotificationMapper notificationMapper;
    @Resource
    private NotificationQueryMapper notificationQueryMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private CommentQueryMapper commentQueryMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserQueryMapper userQueryMapper;
    @Autowired
    private MomentService momentService;

    @Override
    @Transactional
    public void sendNotification(NotificationDBO notification) {
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long userId = loginUser.getUserId();
        Long artistId = loginUser.getArtistId();

        EnumNotificationType type = EnumNotificationType.value2enum.get(notification.getType());
        notification.setSendUserId(userId);
        notification.setArtistId(artistId);
        notification.setCategory(type.getCategory());
        notificationMapper.insertSelective(notification);

        // 评论
        if (Objects.equals(type.getCategory(), EnumNotificationCategory.COMMENT.getValue())) {
            userMapper.updateCommentUnread(userId, artistId, 1);
        }

        // @我
        if (Objects.equals(type.getCategory(), EnumNotificationCategory.AT.getValue())) {
            userMapper.updateAtUnread(userId, artistId, 1);
        }

        // 通知
        if (Objects.equals(type.getCategory(), EnumNotificationCategory.NOTIFICATION.getValue())) {
            userMapper.updateNotificationUnread(userId, artistId, 1);
        }
    }

    @Override
    public BaseResponse<List<AtNotificationDTO>> queryAtInfo(PageRequest request) {
        BaseResponse<List<AtNotificationDTO>> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long userId = loginUser.getUserId();
        Long artistId = loginUser.getArtistId();

        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        int start = PageUtils.getStart(pageNo, pageSize);

        List<NotificationDBO> dboList = notificationQueryMapper.queryByCategory(artistId, EnumNotificationCategory.AT.getValue(), userId, start, pageSize);
        if (CollectionUtils.isEmpty(dboList)) {
            response.setBody(new ArrayList<>());
            return response;
        }

        // 查询发消息的用户
        Set<Long> sendUserIdSet = dboList.stream().map(NotificationDBO::getSendUserId).collect(Collectors.toSet());
        List<UserDBO> sendUserDBOList = userQueryMapper.queryByUserIds(artistId, new ArrayList<>(sendUserIdSet));
        Map<Long, UserDBO> idToUser = sendUserDBOList.stream().collect(Collectors.toMap(UserDBO::getId, Function.identity(), (key1, key2) -> key2));

        // 查询动态
        Set<Long> momentIdSet = dboList.stream().map(NotificationDBO::getMomentId).collect(Collectors.toSet());
        List<MomentDTO> momentDTOList = momentService.queryMoment(artistId, userId, null, null, null, null, new ArrayList<>(momentIdSet), null, null);
        Map<Long, MomentDTO> idToMoment = momentDTOList.stream().collect(Collectors.toMap(MomentDTO::getId, Function.identity(), (key1, key2) -> key2));

        // 查询评论
        Set<Long> commentIdSet = dboList.stream().map(NotificationDBO::getCommentId).collect(Collectors.toSet());
        List<CommentDBO> commentDBOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(commentIdSet)) {
            commentDBOList = commentQueryMapper.queryCommentIds(artistId, new ArrayList<>(commentIdSet));
        }
        Map<Long, CommentDBO> idToComment = commentDBOList.stream().collect(Collectors.toMap(CommentDBO::getId, Function.identity(), (key1, key2) -> key2));

        List<AtNotificationDTO> dtoList = BeanCopyUtils.copy(dboList, AtNotificationDTO.class);
        dtoList.forEach(notification -> {
            if (Objects.equals(notification.getType(), EnumNotificationType.REPOST.getValue())
                    || Objects.equals(notification.getType(), EnumNotificationType.AT_ON_MOMENT.getValue())) {
                notification.setMoment(idToMoment.get(notification.getMomentId()));
            }
            if (Objects.equals(notification.getType(), EnumNotificationType.AT_ON_COMMENT.getValue())
                    || Objects.equals(notification.getType(), EnumNotificationType.AT_ON_CHILDREN_COMMENT.getValue())) {
                MomentDTO momentDTO = new MomentDTO();
                momentDTO.setRepostedMoment(idToMoment.get(notification.getMomentId()));
                momentDTO.setCreateTime(notification.getCreateTime());

                UserDBO sendUserDBO = idToUser.get(notification.getSendUserId());
                momentDTO.setUserId(sendUserDBO.getId());
                momentDTO.setUserName(sendUserDBO.getName());
                momentDTO.setUserAvatarUrl(sendUserDBO.getAvatarUrl());

                CommentDBO commentDBO = idToComment.get(notification.getCommentId());
                momentDTO.setTextContent(commentDBO.getContent());

                notification.setMoment(momentDTO);
            }
        });

        response.setBody(dtoList);
        return response;
    }

    @Override
    public BaseResponse clearNotificationCount(ClearNotificationCountRequest request) {
        BaseResponse response = new BaseResponse();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long userId = loginUser.getUserId();
        Long artistId = loginUser.getArtistId();

        EnumNotificationCategory category = request.getCategory();
        if (Objects.equals(category, EnumNotificationCategory.COMMENT)) {
            userMapper.clearCommentUnread(userId, artistId);
        }
        if (Objects.equals(category, EnumNotificationCategory.AT)) {
            userMapper.clearAtUnread(userId, artistId);
        }
        if (Objects.equals(category, EnumNotificationCategory.NOTIFICATION)) {
            userMapper.clearNotificationUnread(userId, artistId);
        }
        return response;
    }

    @Override
    public BaseResponse<List<CommentNotificationDTO>> queryCommentInfo(PageRequest request) {
        BaseResponse<List<CommentNotificationDTO>> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long userId = loginUser.getUserId();
        Long artistId = loginUser.getArtistId();

        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        int start = PageUtils.getStart(pageNo, pageSize);

        List<NotificationDBO> dboList = notificationQueryMapper.queryByCategory(artistId, EnumNotificationCategory.COMMENT.getValue(), userId, start, pageSize);
        if (CollectionUtils.isEmpty(dboList)) {
            response.setBody(new ArrayList<>());
            return response;
        }

        // 查询发消息的用户
        Set<Long> sendUserIdSet = dboList.stream().map(NotificationDBO::getSendUserId).collect(Collectors.toSet());
        List<UserDBO> sendUserDBOList = userQueryMapper.queryByUserIds(artistId, new ArrayList<>(sendUserIdSet));
        Map<Long, UserDBO> idToUser = sendUserDBOList.stream().collect(Collectors.toMap(UserDBO::getId, Function.identity(), (key1, key2) -> key2));

        // 查询动态
        Set<Long> momentIdSet = dboList.stream().map(NotificationDBO::getMomentId).collect(Collectors.toSet());
        List<MomentDTO> momentDTOList = momentService.queryMoment(artistId, userId, null, null, null, null, new ArrayList<>(momentIdSet), null, null);
        Map<Long, MomentDTO> idToMoment = momentDTOList.stream().collect(Collectors.toMap(MomentDTO::getId, Function.identity(), (key1, key2) -> key2));

        // 查询评论
        Set<Long> commentIdSet = dboList.stream().map(NotificationDBO::getCommentId).collect(Collectors.toSet());
        commentIdSet.addAll(dboList.stream().map(NotificationDBO::getParentCommentId).collect(Collectors.toSet()));
        List<CommentDBO> commentDBOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(commentIdSet)) {
            commentDBOList = commentQueryMapper.queryCommentIds(artistId, new ArrayList<>(commentIdSet));
        }
        Map<Long, CommentDBO> idToComment = commentDBOList.stream().collect(Collectors.toMap(CommentDBO::getId, Function.identity(), (key1, key2) -> key2));

        List<CommentNotificationDTO> dtoList = BeanCopyUtils.copy(dboList, CommentNotificationDTO.class);
        dtoList.forEach(notification -> {
            if (Objects.equals(notification.getType(), EnumNotificationType.COMMENT_TO_MOMENT.getValue())) {
                MomentDTO momentDTO = new MomentDTO();
                momentDTO.setRepostedMoment(idToMoment.get(notification.getMomentId()));
                momentDTO.setCreateTime(notification.getCreateTime());

                UserDBO sendUserDBO = idToUser.get(notification.getSendUserId());
                momentDTO.setUserId(sendUserDBO.getId());
                momentDTO.setUserName(sendUserDBO.getName());
                momentDTO.setUserAvatarUrl(sendUserDBO.getAvatarUrl());

                CommentDBO commentDBO = idToComment.get(notification.getCommentId());
                momentDTO.setTextContent(commentDBO.getContent());

                notification.setMoment(momentDTO);
            }

            if (Objects.equals(notification.getType(), EnumNotificationType.REPLY_TO_MOMENT_COMMENT.getValue())) {
                CommentDBO commentDBO = idToComment.get(notification.getCommentId());
                CommentDBO parentCommentDBO = idToComment.get(notification.getParentCommentId());
                UserDBO userDBO = idToUser.get(notification.getSendUserId());

                notification.setSendUserName(userDBO.getName());
                notification.setSendUserAvatarUrl(userDBO.getAvatarUrl());
                notification.setCommentContent(commentDBO.getContent());
                notification.setParentCommentContent(parentCommentDBO.getContent());
            }
        });

        response.setBody(dtoList);
        return response;
    }
}
