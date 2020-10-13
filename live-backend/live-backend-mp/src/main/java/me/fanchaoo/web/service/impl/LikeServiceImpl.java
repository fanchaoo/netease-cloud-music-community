package me.fanchaoo.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.dbo.*;
import me.fanchaoo.enums.EnumMomentType;
import me.fanchaoo.enums.EnumNotificationType;
import me.fanchaoo.enums.EnumStatus;
import me.fanchaoo.util.AssertUtils;
import me.fanchaoo.util.BeanCopyUtils;
import me.fanchaoo.util.PageUtils;
import me.fanchaoo.web.dao.mapper.*;
import me.fanchaoo.web.dto.LoginUserDTO;
import me.fanchaoo.enums.EnumLikeTargetType;
import me.fanchaoo.web.dto.UserDTO;
import me.fanchaoo.web.request.CancelLikeRequest;
import me.fanchaoo.web.request.LikeRequest;
import me.fanchaoo.web.request.QueryLikeUserRequest;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.LikeService;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    private LikeRelationMapper likeRelationMapper;
    @Resource
    private LikeRelationQueryMapper likeRelationQueryMapper;
    @Resource
    private MomentMapper momentMapper;
    @Resource
    private MomentQueryMapper momentQueryMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private CommentQueryMapper commentQueryMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserQueryMapper userQueryMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    @Transactional
    public void like(LikeRequest request) {
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        EnumLikeTargetType targetType = request.getTargetType();
        Long targetId = request.getTargetId();

        likeRelationMapper.insertSelective(new LikeRelationDBO(userId, targetType.getValue(), targetId, artistId));
        if (Objects.equals(targetType, EnumLikeTargetType.MOMENT)) {

            MomentDBO momentDBO = momentQueryMapper.getById(artistId, targetId, EnumStatus.NORMAL.getValue());
            AssertUtils.notNull(momentDBO, "动态不存在：" + targetId);
            momentMapper.updateLikeCount(artistId, targetId, 1);

            // 发消息
            if (Objects.equals(momentDBO.getType(), EnumMomentType.VIDEO.getValue())) {
                notificationService.sendNotification(NotificationDBO.builder()
                        .type(EnumNotificationType.LIKE_VIDEO.getValue())
                        .momentId(targetId)
                        .receiveUserId(momentQueryMapper.getUserId(artistId, targetId))
                        .build());
            } else {
                notificationService.sendNotification(NotificationDBO.builder()
                        .type(EnumNotificationType.LIKE_MOMENT.getValue())
                        .momentId(targetId)
                        .receiveUserId(momentQueryMapper.getUserId(artistId, targetId))
                        .build());
            }
        }

        if (Objects.equals(targetType, EnumLikeTargetType.COMMENT)) {
            CommentDBO commentDBO = commentQueryMapper.getById(artistId, targetId, EnumStatus.NORMAL.getValue());
            AssertUtils.notNull(commentDBO, "评论不存在：" + targetId);
            commentMapper.updateLikeCount(artistId, targetId, 1);

            // 发消息
            if (Objects.equals(commentDBO.getParentId(), 0L)) {
                notificationService.sendNotification(NotificationDBO.builder()
                        .type(EnumNotificationType.LIKE_COMMENT.getValue())
                        .momentId(targetId)
                        .receiveUserId(commentQueryMapper.getUserId(artistId, targetId))
                        .build());
            } else {
                notificationService.sendNotification(NotificationDBO.builder()
                        .type(EnumNotificationType.LIKE_CHILDREN_COMMENT.getValue())
                        .momentId(targetId)
                        .parentCommentId(commentDBO.getParentId())
                        .receiveUserId(commentQueryMapper.getUserId(artistId, targetId))
                        .build());
            }
        }
    }

    @Override
    @Transactional
    public void cancelLike(CancelLikeRequest request) {
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        EnumLikeTargetType targetType = request.getTargetType();
        Long targetId = request.getTargetId();

        likeRelationMapper.delete(new LikeRelationDBO(userId, targetType.getValue(), targetId, artistId));
        if (Objects.equals(targetType, EnumLikeTargetType.MOMENT)) {
            momentMapper.updateLikeCount(artistId, targetId, -1);
        }
        if (Objects.equals(targetType, EnumLikeTargetType.COMMENT)) {
            commentMapper.updateLikeCount(artistId, targetId, -1);
        }
    }

    @Override
    public BaseResponse<List<UserDTO>> queryLikeUser(QueryLikeUserRequest request) {
        BaseResponse<List<UserDTO>> response = new BaseResponse<>();
        List<UserDTO> dtoList = new ArrayList<>();
        response.setBody(dtoList);
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();

        EnumLikeTargetType targetType = request.getTargetType();
        Long targetId = request.getTargetId();

        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        int start = PageUtils.getStart(pageNo, pageSize);
        List<LikeRelationDBO> likeRelationDBOList = likeRelationQueryMapper.queryByTargetTypeTargetId(artistId, targetType.getValue(), targetId, start, pageSize);
        if (CollectionUtils.isEmpty(likeRelationDBOList)) {
            return response;
        }

        List<Long> userIdList = likeRelationDBOList.stream().map(LikeRelationDBO::getUserId).collect(Collectors.toList());
        List<UserDBO> userDBOList = userQueryMapper.queryByUserIds(artistId, userIdList);

        response.setBody(BeanCopyUtils.copy(userDBOList, UserDTO.class));
        return response;
    }
}
