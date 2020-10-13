package me.fanchaoo.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.constant.HtmlTemplateConstant;
import me.fanchaoo.dbo.*;
import me.fanchaoo.enums.*;
import me.fanchaoo.exception.BusinessException;
import me.fanchaoo.util.*;
import me.fanchaoo.web.dao.mapper.*;
import me.fanchaoo.web.dto.CommentDTO;
import me.fanchaoo.web.dto.GroupCountDTO;
import me.fanchaoo.web.dto.LoginUserDTO;
import me.fanchaoo.web.dto.MomentDTO;
import me.fanchaoo.web.request.GetCommentDetailRequest;
import me.fanchaoo.web.request.PublishCommentRequest;
import me.fanchaoo.web.request.DeleteCommentRequest;
import me.fanchaoo.web.request.QueryMomentCommentRequest;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.CommentService;
import me.fanchaoo.web.service.NotificationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private CommentQueryMapper commentQueryMapper;
    @Resource
    private MomentMapper momentMapper;
    @Resource
    private MomentQueryMapper momentQueryMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserQueryMapper userQueryMapper;
    @Resource
    private LikeRelationMapper likeRelationMapper;
    @Resource
    private LikeRelationQueryMapper likeRelationQueryMapper;
    @Resource
    private FollowRelationMapper followRelationMapper;
    @Resource
    private FollowRelationQueryMapper followRelationQueryMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    @Transactional
    public BaseResponse<Long> publishComment(PublishCommentRequest request) {

        BaseResponse<Long> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        Long momentId = request.getMomentId();
        Long parentId = request.getParentId();
        Long replyToId = request.getReplyToId();
        String content = request.getContent();

        MomentDBO momentDBO = momentQueryMapper.getById(artistId, momentId, EnumStatus.NORMAL.getValue());
        AssertUtils.notNull(momentDBO, "moment不存在：" + momentId);

        if (!Objects.equals(parentId, 0L)) {
            // 子评论
            int count = commentQueryMapper.exists(artistId, parentId);
            if (count == 0) {
                throw new BusinessException(EnumResponseCode.COMMENT_NOT_EXISTS.getValue(), EnumResponseCode.COMMENT_NOT_EXISTS.getDesc());
            }
        }
        if (!Objects.equals(replyToId, 0L)) {
            // 子评论中回复某人
            int count = commentQueryMapper.exists(artistId, replyToId);
            if (count == 0) {
                throw new BusinessException(EnumResponseCode.COMMENT_NOT_EXISTS.getValue(), EnumResponseCode.COMMENT_NOT_EXISTS.getDesc());
            }
        }

        // 处理文本
        Set<Long> atUserIdList = new HashSet<>();
        String newTextContent = handlePublishMomentTextContent(artistId, content, atUserIdList);

        // 插入评论
        CommentDBO commentDBO = new CommentDBO();
        commentDBO.setArtistId(artistId);
        commentDBO.setUserId(userId);
        commentDBO.setMomentId(momentId);
        commentDBO.setContent(newTextContent);
        commentDBO.setParentId(parentId);
        commentDBO.setReplyToId(Objects.equals(parentId, 0L) ? 0L : replyToId);
        commentMapper.insertSelective(commentDBO);
        momentMapper.updateCommentCount(artistId, momentId, 1);

        // @用户 发消息
        if (CollectionUtils.isNotEmpty(atUserIdList)) {
            if (Objects.equals(commentDBO.getParentId(), 0L)) {
                atUserIdList.forEach(atUserId -> {
                    notificationService.sendNotification(NotificationDBO.builder()
                            .type(EnumNotificationType.AT_ON_COMMENT.getValue())
                            .momentId(momentId)
                            .commentId(commentDBO.getId())
                            .receiveUserId(atUserId)
                            .build());
                });
            } else {
                atUserIdList.forEach(atUserId -> {
                    notificationService.sendNotification(NotificationDBO.builder()
                            .type(EnumNotificationType.AT_ON_CHILDREN_COMMENT.getValue())
                            .momentId(momentId)
                            .parentCommentId(commentDBO.getParentId())
                            .commentId(commentDBO.getId())
                            .receiveUserId(atUserId)
                            .build());
                });
            }
        }

        // 发消息
        if (Objects.equals(commentDBO.getParentId(), 0L)) {
            notificationService.sendNotification(NotificationDBO.builder()
                    .type(EnumNotificationType.COMMENT_TO_MOMENT.getValue())
                    .momentId(momentId)
                    .commentId(commentDBO.getId())
                    .receiveUserId(momentQueryMapper.getUserId(artistId, momentId))
                    .build());
        } else {
            notificationService.sendNotification(NotificationDBO.builder()
                    .type(EnumNotificationType.REPLY_TO_MOMENT_COMMENT.getValue())
                    .momentId(momentId)
                    .parentCommentId(commentDBO.getParentId())
                    .commentId(commentDBO.getId())
                    .receiveUserId(commentQueryMapper.getUserId(artistId, commentDBO.getParentId()))
                    .build());
        }
        response.setBody(commentDBO.getId());
        return response;
    }

    private String handlePublishMomentTextContent(Long artistId, String textContent, Set<Long> atUserIdList) {
        // 处理@
        Set<String> matchedAtSet = new HashSet<>();
        Set<String> newMatchedAtSet = new HashSet<>();
        textContent = ReplaceUtils.translate(textContent, HtmlTemplateConstant.AT_TEMPLATE, HtmlTemplateConstant.AT_PATTERN, new HashMap<>(), matchedAtSet);
        matchedAtSet.forEach(userName -> {
            if (userName.length() > 1) {
                String newUserName = userName.substring(1);
                newMatchedAtSet.add(newUserName);
            }
        });
        if (CollectionUtils.isNotEmpty(newMatchedAtSet)) {
            List<UserDBO> userDBOList = userQueryMapper.queryByNames(artistId, newMatchedAtSet);
            if (CollectionUtils.isNotEmpty(userDBOList)) {
                atUserIdList.addAll(userDBOList.stream().map(UserDBO::getId).collect(Collectors.toSet()));
            }
        }
        return textContent;
    }


    @Override
    @Transactional
    public BaseResponse deleteComment(DeleteCommentRequest request) {
        BaseResponse response = new BaseResponse();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();
        Long commentId = request.getCommentId();

        CommentDBO commentDBO = commentQueryMapper.getById(artistId, commentId, EnumStatus.NORMAL.getValue());
        AssertUtils.notNull(commentDBO, "comment不存在：" + commentId);

        commentMapper.updataStatus(artistId, userId, commentId, EnumStatus.DELETED.getValue());
        momentMapper.updateCommentCount(artistId, commentDBO.getMomentId(), -1);
        return response;
    }

    @Override
    public BaseResponse<List<CommentDTO>> queryMomentComment(QueryMomentCommentRequest request) {
        BaseResponse<List<CommentDTO>> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        // 查询评论
        Long momentId = request.getMomentId();
        Long parentId = request.getParentId();
        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        int start = PageUtils.getStart(pageNo, pageSize);
        List<CommentDTO> commentDTOList = BeanCopyUtils.copy(commentQueryMapper.queryComment(artistId, momentId, parentId, start, pageSize), CommentDTO.class);
        if (CollectionUtils.isEmpty(commentDTOList)) {
            response.setBody(commentDTOList);
            return response;
        }

        // 绑定用户信息，点赞，回复数目
        bindCommentExtraInfo(artistId, userId, commentDTOList);

        if (!Objects.equals(parentId, 0L)) {
            Set<Long> replyToCommentIdSet = commentDTOList.stream().map(CommentDTO::getReplyToId).filter(x -> !Objects.equals(x, 0L)).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(replyToCommentIdSet)) {
                List<CommentDTO> replyToCommentDTOList = BeanCopyUtils.copy(commentQueryMapper.queryCommentIds(artistId, new ArrayList<>(replyToCommentIdSet)), CommentDTO.class);
                if (CollectionUtils.isNotEmpty(replyToCommentDTOList)) {
                    bindCommentExtraInfo(artistId, userId, replyToCommentDTOList);
                }

                Map<Long, CommentDTO> idToComment = replyToCommentDTOList.stream().collect(Collectors.toMap(CommentDTO::getId, Function.identity(), (key1, key2) -> key2));
                commentDTOList.forEach(commentDTO -> {
                    Long replyToId = commentDTO.getReplyToId();
                    if (!Objects.equals(replyToId, 0L)) {
                        commentDTO.setReplyToComment(idToComment.get(replyToId));
                    }
                });
            }
        }

        response.setBody(commentDTOList);
        return response;
    }

    @Override
    public BaseResponse<CommentDTO> getCommentDetail(GetCommentDetailRequest request) {
        BaseResponse<CommentDTO> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        // 查询评论
        Long commentId = request.getCommentId();
        CommentDBO commentDBO = commentQueryMapper.getById(artistId, commentId, EnumStatus.NORMAL.getValue());
        CommentDTO commentDTO = BeanCopyUtils.copy(commentDBO, CommentDTO.class);
        if (commentDTO == null) {
            return response;
        }

        // 绑定用户信息，点赞，回复数目
        bindCommentExtraInfo(artistId, userId, Collections.singletonList(commentDTO));

        response.setBody(commentDTO);
        return response;
    }

    private void bindCommentExtraInfo(Long artistId, Long userId, List<CommentDTO> commentDTOList) {

        if (CollectionUtils.isEmpty(commentDTOList)) {
            return;
        }

        // 用户信息
        Set<Long> userIdSet = commentDTOList.stream().map(CommentDTO::getUserId).collect(Collectors.toSet());
        List<UserDBO> userDBOList = userQueryMapper.queryByUserIds(artistId, new ArrayList<>(userIdSet));
        Map<Long, UserDBO> idToUser = userDBOList.stream().collect(Collectors.toMap(UserDBO::getId, Function.identity(), (key1, key2) -> key2));
        commentDTOList.forEach(comment -> {
            UserDBO userDBO = idToUser.get(comment.getUserId());
            comment.setUserName(userDBO.getName());
            comment.setUserAvatarUrl(userDBO.getAvatarUrl());
        });

        // 是否关注
        List<FollowRelationDBO> followRelationDBOList = followRelationQueryMapper.queryByUserTargetTypeTargetIds(artistId, userId, EnumFollowTargetType.USER.getValue(), new ArrayList<>(userIdSet));
        Set<Long> followUserIdSet = followRelationDBOList.stream().map(FollowRelationDBO::getTargetId).collect(Collectors.toSet());
        commentDTOList.forEach(comment -> {
            comment.setHasFollowed(followUserIdSet.contains(comment.getUserId()));
        });

        // 是否点赞
        List<Long> commentIdList = commentDTOList.stream().map(CommentDTO::getId).collect(Collectors.toList());
        List<LikeRelationDBO> likeRelationDBOList = likeRelationQueryMapper.queryByUserTargetTypeTargetIds(artistId, userId, EnumLikeTargetType.COMMENT.getValue(), commentIdList);
        Map<Long, LikeRelationDBO> commentIdToLikeRelation = likeRelationDBOList.stream().collect(Collectors.toMap(LikeRelationDBO::getTargetId, Function.identity(), (key1, key2) -> key2));
        commentDTOList.forEach(comment -> {
            comment.setHasLiked(commentIdToLikeRelation.get(comment.getId()) != null);
        });

        // 回复个数
        List<GroupCountDTO> countList = commentQueryMapper.countByParentId(artistId, commentIdList);
        Map<Long, Integer> idToReplyCount = countList.stream().collect(Collectors.toMap(GroupCountDTO::getId, GroupCountDTO::getCount, (key1, key2) -> key2));
        commentDTOList.forEach(comment -> {
            comment.setReplyCount(idToReplyCount.get(comment.getId()));
        });

    }
}
