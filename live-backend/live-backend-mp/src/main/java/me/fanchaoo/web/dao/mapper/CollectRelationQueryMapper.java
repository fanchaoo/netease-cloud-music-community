package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.CollectRelationDBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectRelationQueryMapper {
    CollectRelationDBO selectByPrimaryKey(Long id);

    List<CollectRelationDBO> queryByUserTargetTypeTargetIds(@Param("artistId") Long artistId, @Param("userId") Long userId,
                                                            @Param("targetType") String targetType, @Param("targetIdList") List<Long> targetIdList);

    List<CollectRelationDBO> queryByUserTargetType(@Param("artistId") Long artistId, @Param("userId") Long userId,
                                                   @Param("targetType") String targetType,
                                                   @Param("start") Integer start, @Param("pageSize") Integer pageSize);

}