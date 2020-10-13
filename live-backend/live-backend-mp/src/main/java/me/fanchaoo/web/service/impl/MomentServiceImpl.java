package me.fanchaoo.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.constant.HtmlTemplateConstant;
import me.fanchaoo.dbo.*;
import me.fanchaoo.enums.*;
import me.fanchaoo.exception.BusinessException;
import me.fanchaoo.util.*;
import me.fanchaoo.web.dao.mapper.*;
import me.fanchaoo.web.dto.*;
import me.fanchaoo.web.request.*;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.MomentService;
import me.fanchaoo.web.service.NotificationService;
import me.fanchaoo.web.util.oss.OssConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MomentServiceImpl implements MomentService {


    @Autowired
    private OssConfig ossConfig;
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
    private TagRelationMapper tagRelationMapper;
    @Resource
    private TagRelationQueryMapper tagRelationQueryMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserQueryMapper userQueryMapper;
    @Resource
    private LikeRelationMapper likeRelationMapper;
    @Resource
    private LikeRelationQueryMapper likeRelationQueryMapper;
    @Resource
    private CollectRelationMapper collectRelationMapper;
    @Resource
    private CollectRelationQueryMapper collectRelationQueryMapper;
    @Resource
    private FollowRelationMapper followRelationMapper;
    @Resource
    private FollowRelationQueryMapper followRelationQueryMapper;
    @Resource
    private TopicMapper topicMapper;
    @Resource
    private TopicQueryMapper topicQueryMapper;
    @Resource
    private TopicRelationMapper topicRelationMapper;
    @Resource
    private TopicRelationQueryMapper topicRelationQueryMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    public BaseResponse<List<MomentDTO>> querySquareMoment(QuerySquareMomentRequest request) {
        BaseResponse<List<MomentDTO>> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long userId = loginUser.getUserId();
        Long artistId = loginUser.getArtistId();

        String momentType = Objects.nonNull(request.getMomentType()) ? request.getMomentType().getValue() : null;
        Long repostMomentId = request.getRepostMomentId();
        List<Long> userIdList = request.getUserIdList();
        Long activityId = request.getActivityId();
        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        int start = PageUtils.getStart(pageNo, pageSize);

        List<MomentDTO> momentDTOList = queryMoment(artistId, userId, momentType, repostMomentId, userIdList, activityId, null, start, pageSize);
        response.setBody(momentDTOList);
        return response;
    }

    @Override
    public List<MomentDTO> queryMoment(Long artistId, Long userId, String momentType, Long repostMomentId, List<Long> userIdList, Long activityId, List<Long> idList, Integer start, Integer pageSize) {
        List<MomentDBO> momentDBOList = momentQueryMapper.queryMoment(artistId, momentType, repostMomentId, userIdList, activityId, idList, start, pageSize);
        List<MomentDTO> momentDTOList = BeanCopyUtils.copy(momentDBOList, MomentDTO.class);
        if (CollectionUtils.isEmpty(momentDTOList)) {
            return momentDTOList;
        }

        // 绑定用户信息，图片信息，视频信息
        bindMomentImageVideoInfo(userId, artistId, momentDTOList);

        // 处理转发moment
        List<MomentDTO> repostMomentList = momentDTOList.stream().filter(x -> !Objects.equals(x.getRepostMomentId(), 0L)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(repostMomentList)) {

            List<Long> repostMomentIdList = new ArrayList<>(repostMomentList.stream().map(MomentDTO::getRepostMomentId).collect(Collectors.toSet()));
            List<MomentDBO> repostedMomentDBOList = momentQueryMapper.queryMomentByIds(artistId, repostMomentIdList);
            List<MomentDTO> repostedMomentDTOList = BeanCopyUtils.copy(repostedMomentDBOList, MomentDTO.class);

            bindMomentImageVideoInfo(userId, artistId, repostedMomentDTOList);
            Map<Long, MomentDTO> idToRepostedMoment = repostedMomentDTOList.stream().collect(Collectors.toMap(MomentDTO::getId, Function.identity(), (key1, key2) -> key2));
            repostMomentList.forEach(moment -> {
                moment.setRepostedMoment(idToRepostedMoment.get(moment.getRepostMomentId()));
            });
        }
        return momentDTOList;
    }

    @Override
    public BaseResponse<List<MomentDTO>> queryFriendMoment(PageRequest request) {
        BaseResponse<List<MomentDTO>> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long userId = loginUser.getUserId();
        Long artistId = loginUser.getArtistId();

        List<FollowRelationDBO> followRelationDBOList = followRelationQueryMapper.queryByUserTargetType(artistId, userId, EnumFollowTargetType.USER.getValue(), null, null);
        if (CollectionUtils.isEmpty(followRelationDBOList)) {
            response.setBody(new ArrayList<>());
            return response;
        }

        List<Long> followUserIdList = followRelationDBOList.stream().map(FollowRelationDBO::getTargetId).collect(Collectors.toList());
        QuerySquareMomentRequest req = BeanCopyUtils.copy(request, QuerySquareMomentRequest.class);
        req.setUserIdList(followUserIdList);
        return querySquareMoment(req);
    }

    @Override
    public BaseResponse<List<MomentDTO>> queryTopicMoment(QueryTopicMomentRequest request) {
        BaseResponse<List<MomentDTO>> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long userId = loginUser.getUserId();
        Long artistId = loginUser.getArtistId();
        Long topicId = request.getTopicId();
        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        int start = PageUtils.getStart(pageNo, pageSize);

        List<TopicRelationDBO> topicRelationDBOList = topicRelationQueryMapper.queryByTopicId(artistId, topicId, start, pageSize);
        if (CollectionUtils.isEmpty(topicRelationDBOList)) {
            response.setBody(new ArrayList<>());
            return response;
        }

        List<Long> momentIdList = topicRelationDBOList.stream().map(TopicRelationDBO::getMomentId).collect(Collectors.toList());
        List<MomentDTO> momentDTOList = queryMoment(artistId, userId, null, null, null, null, momentIdList, null, null);

        response.setBody(momentDTOList);
        return response;
    }

    private void bindMomentImageVideoInfo(Long userId, Long artistId, List<MomentDTO> commonMomentList) {
        if (CollectionUtils.isEmpty(commonMomentList)) {
            return;
        }

        Map<String, List<MomentDTO>> typeToMomentList = commonMomentList.stream().collect(Collectors.groupingBy(MomentDTO::getType));
        // 用户信息
        List<Long> userIdList = new ArrayList<>(commonMomentList.stream().map(MomentDTO::getUserId).collect(Collectors.toSet()));
        List<UserDBO> userDBOList = userQueryMapper.queryByUserIds(artistId, userIdList);
        Map<Long, UserDBO> idToUser = userDBOList.stream().collect(Collectors.toMap(UserDBO::getId, Function.identity(), (key1, key2) -> key2));
        commonMomentList.forEach(moment -> {
            UserDBO userDBO = idToUser.get(moment.getUserId());
            moment.setUserName(userDBO.getName());
            moment.setUserAvatarUrl(userDBO.getAvatarUrl());
        });

        // 图片动态
        List<MomentDTO> imageMomentList = typeToMomentList.get(EnumMomentType.IMAGE.getValue());
        if (CollectionUtils.isNotEmpty(imageMomentList)) {
            Map<Long, MomentDTO> idToMoment = imageMomentList.stream().collect(Collectors.toMap(MomentDTO::getId, Function.identity(), (key1, key2) -> key2));
            List<Long> momentIdList = new ArrayList<>(idToMoment.keySet());
            List<MomentImageDBO> imageDBOList = momentImageQueryMapper.queryByMomentIds(artistId, momentIdList);
            Map<Long, List<MomentImageDBO>> momentToImageList = imageDBOList.stream().collect(Collectors.groupingBy(MomentImageDBO::getMomentId));
            momentToImageList.forEach((momentId, imageList) -> {
                MomentDTO momentDTO = idToMoment.get(momentId);
                momentDTO.setImageList(BeanCopyUtils.copy(imageList, MomentImageDTO.class));
            });
        }

        // 视频动态
        List<MomentDTO> videoMomentList = typeToMomentList.get(EnumMomentType.VIDEO.getValue());
        if (CollectionUtils.isNotEmpty(videoMomentList)) {
            Map<Long, MomentDTO> idToMoment = videoMomentList.stream().collect(Collectors.toMap(MomentDTO::getId, Function.identity(), (key1, key2) -> key2));
            List<Long> momentIdList = new ArrayList<>(idToMoment.keySet());
            List<MomentVideoDBO> videoDBOList = momentVideoQueryMapper.queryByMomentIds(artistId, momentIdList);
            List<MomentVideoDTO> videoDTOList = BeanCopyUtils.copy(videoDBOList, MomentVideoDTO.class);
            Map<Long, MomentVideoDTO> momentToVideo = videoDTOList.stream().collect(Collectors.toMap(MomentVideoDBO::getMomentId, Function.identity(), (key1, key2) -> key2));
            momentToVideo.forEach((momentId, video) -> {
                MomentDTO momentDTO = idToMoment.get(momentId);
                momentDTO.setVideo(video);
            });

            // 是否收藏
            if (CollectionUtils.isNotEmpty(videoDTOList)) {
                List<Long> videoIdList = videoDTOList.stream().map(MomentVideoDTO::getId).collect(Collectors.toList());
                List<CollectRelationDBO> collectRelationDBOList = collectRelationQueryMapper.queryByUserTargetTypeTargetIds(artistId, userId, EnumCollectTargetType.VIDEO.getValue(), videoIdList);
                Set<Long> videoIdSet = collectRelationDBOList.stream().map(CollectRelationDBO::getTargetId).collect(Collectors.toSet());
                videoDTOList.forEach(video -> {
                    video.setHasCollected(videoIdSet.contains(video.getId()));
                });
            }
        }

        // 是否点赞
        List<Long> momentIdList = commonMomentList.stream().map(MomentDTO::getId).collect(Collectors.toList());
        List<LikeRelationDBO> likeRelationDBOList = likeRelationQueryMapper.queryByUserTargetTypeTargetIds(artistId, userId, EnumLikeTargetType.MOMENT.getValue(), momentIdList);
        Set<Long> momentIdSet = likeRelationDBOList.stream().map(LikeRelationDBO::getTargetId).collect(Collectors.toSet());
        commonMomentList.forEach(moment -> {
            moment.setHasLiked(momentIdSet.contains(moment.getId()));
        });

        // 是否关注
        Set<Long> userIdSet = commonMomentList.stream().map(MomentDTO::getUserId).collect(Collectors.toSet());
        List<FollowRelationDBO> followRelationDBOList = followRelationQueryMapper.queryByUserTargetTypeTargetIds(artistId, userId, EnumFollowTargetType.USER.getValue(), new ArrayList<>(userIdSet));
        Set<Long> followUserIdSet = followRelationDBOList.stream().map(FollowRelationDBO::getTargetId).collect(Collectors.toSet());
        commonMomentList.forEach(moment -> {
            moment.setHasFollowed(followUserIdSet.contains(moment.getUserId()));
        });
    }

    @Override
    public BaseResponse<MomentDetailDTO> getMomentDetail(GetMomentDetailRequest request) {
        log.info("查看动态：" + request.getMomentId());
        BaseResponse<MomentDetailDTO> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        Long momentId = request.getMomentId();
        MomentDBO momentDBO = momentQueryMapper.getById(artistId, momentId, EnumStatus.NORMAL.getValue());
        AssertUtils.notNull(momentDBO, "moment不存在：" + momentId);
        MomentDTO momentDTO = BeanCopyUtils.copy(momentDBO, MomentDTO.class);

        bindMomentImageVideoInfo(userId, artistId, Collections.singletonList(momentDTO));

        Long repostMomentId = momentDTO.getRepostMomentId();
        if (!Objects.equals(repostMomentId, 0L)) {
            // 转发动态
            MomentDBO repostedomentDBO = momentQueryMapper.getById(artistId, repostMomentId, EnumStatus.NORMAL.getValue());
            AssertUtils.notNull(repostedomentDBO, "moment不存在：" + momentId);
            MomentDTO repostedomentDTO = BeanCopyUtils.copy(repostedomentDBO, MomentDTO.class);
            bindMomentImageVideoInfo(userId, artistId, Collections.singletonList(repostedomentDTO));
            momentDTO.setRepostedMoment(repostedomentDTO);
        }

        momentMapper.updateViewCount(artistId, momentId, 1);

        MomentDetailDTO momentDetailDTO = BeanCopyUtils.copy(momentDTO, MomentDetailDTO.class);
        response.setBody(momentDetailDTO);
        return response;
    }

    @Override
    @Transactional
    public void publishMoment(PublishMomentRequest request) {

        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();
        EnumMomentType momentType = request.getMomentType();

        // 处理文本
        Set<Long> topicIdSet = new HashSet<>();
        Set<Long> atUserIdList = new HashSet<>();
        String newTextContent = handlePublishMomentTextContent(artistId, userId, request.getTextContent(), topicIdSet, atUserIdList);

        MomentDBO momentDBO = new MomentDBO();
        momentDBO.setArtistId(artistId);
        momentDBO.setUserId(userId);
        momentDBO.setTextContent(newTextContent);
        momentDBO.setType(momentType.getValue());
        momentDBO.setActivityId(request.getActivityId());
        momentMapper.insertSelective(momentDBO);

        Long momentId = momentDBO.getId();
        if (Objects.equals(momentType, EnumMomentType.IMAGE)) {
            // 图文
            List<PublishMomentRequest.Image> imageList = request.getImageList();
            List<MomentImageDBO> momentImageDBOList = imageList.stream().map(image -> new MomentImageDBO(momentId, image.getUrl(), image.getWidth(), image.getHeight(), artistId)).collect(Collectors.toList());
            momentImageMapper.insertBatch(momentImageDBOList);
        }

        if (Objects.equals(momentType, EnumMomentType.VIDEO)) {
            // 视频
            PublishMomentRequest.Video video = request.getVideo();
            if (Objects.isNull(video)) {
                throw new BusinessException(EnumResponseCode.VIDEO_PARAM_REQUIRES.getValue(), EnumResponseCode.VIDEO_PARAM_REQUIRES.getDesc());
            }

            MomentVideoDBO momentVideoDBO = BeanCopyUtils.copy(video, MomentVideoDBO.class);
            momentVideoDBO.setMomentId(momentId);
            momentVideoDBO.setArtistId(artistId);
            momentVideoMapper.insertSelective(momentVideoDBO);
        }

        // 标签
        List<Long> tagIdList = request.getTagIdList();
        if (CollectionUtils.isNotEmpty(tagIdList)) {
            List<TagRelationDBO> tagRelationDBOList = tagIdList.stream().map(tagId -> new TagRelationDBO(tagId, EnumEntityType.MOMENT.getValue(), momentId, artistId)).collect(Collectors.toList());
            tagRelationMapper.insertBatch(tagRelationDBOList);
        }

        // 话题
        if (CollectionUtils.isNotEmpty(topicIdSet)) {
            List<TopicRelationDBO> dboList = new ArrayList<>();
            topicIdSet.forEach(topicId -> {
                TopicRelationDBO dbo = new TopicRelationDBO();
                dbo.setArtistId(artistId);
                dbo.setTopicId(topicId);
                dbo.setMomentId(momentId);
                dbo.setUserId(userId);
                dboList.add(dbo);
            });
            topicRelationMapper.insertBatch(dboList);
        }

        // @用户
        if (CollectionUtils.isNotEmpty(atUserIdList)) {
            // 发通知
            atUserIdList.forEach(atUserId -> {
                notificationService.sendNotification(NotificationDBO.builder()
                        .type(EnumNotificationType.AT_ON_MOMENT.getValue())
                        .momentId(momentId)
                        .receiveUserId(atUserId)
                        .build());
            });
        }
    }

    private String handlePublishMomentTextContent(Long artistId, Long userId, String textContent, Set<Long> topicIdSet, Set<Long> atUserIdList) {
        // 处理话题
        Set<String> matchedTopicSet = new HashSet<>();
        textContent = ReplaceUtils.translate(textContent, HtmlTemplateConstant.TOPIC_TEMPLATE, HtmlTemplateConstant.TOPIC_PATTERN, new HashMap<>(), matchedTopicSet);
        Set<String> newMatchedTopicSet = new HashSet<>();
        matchedTopicSet.forEach(topicName -> {
            if (topicName.length() > 2) {
                String newTopicName = topicName.substring(1, topicName.length() - 1);
                newMatchedTopicSet.add(newTopicName);
            }
        });
        if (CollectionUtils.isNotEmpty(newMatchedTopicSet)) {
            List<TopicDBO> topicDBOList = topicQueryMapper.queryByNames(artistId, newMatchedTopicSet);
            // 更新
            if (CollectionUtils.isNotEmpty(topicDBOList)) {
                topicDBOList.forEach(topic -> {
                    topic.setLastUpdateUserId(userId);
                    topicMapper.updateByPrimaryKeySelective(topic);
                });
                topicIdSet.addAll(topicDBOList.stream().map(TopicDBO::getId).collect(Collectors.toSet()));
            }
            // 插入
            Set<String> nameSet = topicDBOList.stream().map(TopicDBO::getName).collect(Collectors.toSet());
            newMatchedTopicSet.removeAll(nameSet);
            if (CollectionUtils.isNotEmpty(newMatchedTopicSet)) {
                List<TopicDBO> dboList = new ArrayList<>();
                newMatchedTopicSet.forEach(topicName -> {
                    TopicDBO dbo = new TopicDBO();
                    dbo.setArtistId(artistId);
                    dbo.setCreateUserId(userId);
                    dbo.setLastUpdateUserId(userId);
                    dbo.setName(topicName);
                    dbo.setCoverUrl(ossConfig.getDomainUrl() + UserContext.getLoginUser().getArtistName() + "/image/" + ImageGenerateUtils.getImageName());
                    dboList.add(dbo);
                });
                topicMapper.insertBatch(dboList);
                topicIdSet.addAll(dboList.stream().map(TopicDBO::getId).collect(Collectors.toSet()));
            }
        }

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
    public void deleteMoment(DeleteMomentRequest request) {
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();
        Long userId = loginUser.getUserId();

        momentMapper.deleteByPrimaryKey(request.getMomentId(), userId, artistId);
    }

    @Override
    public BaseResponse<TreeMap<String, List<MomentImageDTO>>> querySquareImage(QuerySquareImageRequest request) {
        BaseResponse<TreeMap<String, List<MomentImageDTO>>> response = new BaseResponse<>();
        LoginUserDTO loginUser = UserContext.getLoginUser();
        Long artistId = loginUser.getArtistId();

        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        int start = PageUtils.getStart(pageNo, pageSize);
        List<MomentImageDBO> imageDBOList = momentImageQueryMapper.queryImage(artistId, start, pageSize);
        List<MomentImageDTO> imageDTOList = BeanCopyUtils.copy(imageDBOList, MomentImageDTO.class);
        Map<String, List<MomentImageDTO>> dateToImageList = imageDTOList.stream().collect(Collectors.groupingBy(x -> x.getCreateTime().toString("yyyy-MM-dd")));

        TreeMap<String, List<MomentImageDTO>> map = new TreeMap<>(Comparator.reverseOrder());
        map.putAll(dateToImageList);
        response.setBody(map);
        return response;
    }
}
