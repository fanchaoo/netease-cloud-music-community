package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.FollowRelationDBO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface FollowRelationQueryMapper {
    FollowRelationDBO selectByPrimaryKey(Long id);

    List<FollowRelationDBO> queryByUserTargetTypeTargetIds(@Param("artistId") Long artistId, @Param("userId") Long userId,
                                                           @Param("targetType") String targetType, @Param("targetIdList") List<Long> targetIdList);

    List<FollowRelationDBO> queryByUserTargetType(@Param("artistId") Long artistId, @Param("userId") Long userId,
                                                  @Param("targetType") String targetType,
                                                  @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<FollowRelationDBO> queryByUsersTargetTypeTargetId(@Param("artistId") Long artistId, @Param("userIdList") List<Long> userIdList,
                                                           @Param("targetType") String targetType, @Param("targetId") Long targetId);


    List<FollowRelationDBO> queryByTargetTypeTargetId(@Param("artistId") Long artistId, @Param("targetType") String targetType,
                                                      @Param("targetId") Long targetId,
                                                      @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    int countByUserTargetType(@Param("artistId") Long artistId, @Param("userId") Long userId,
                              @Param("targetType") String targetType);


    int countByTargetTypeTargetId(@Param("artistId") Long artistId, @Param("targetType") String targetType,
                                  @Param("targetId") Long targetId);

}