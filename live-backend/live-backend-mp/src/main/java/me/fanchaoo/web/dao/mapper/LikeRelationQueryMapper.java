package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.LikeRelationDBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeRelationQueryMapper {
    LikeRelationDBO selectByPrimaryKey(Long id);

    List<LikeRelationDBO> queryByUserTargetTypeTargetIds(@Param("artistId") Long artistId, @Param("userId") Long userId,
                                                         @Param("targetType") String targetType, @Param("targetIdList") List<Long> targetIdList);

    List<LikeRelationDBO> queryByTargetTypeTargetId(@Param("artistId") Long artistId, @Param("targetType") String targetType,
                                                    @Param("targetId") Long targetId, @Param("start") int start,
                                                    @Param("pageSize") Integer pageSize);

}