package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.MomentDBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MomentQueryMapper {
    MomentDBO selectByPrimaryKey(Long id);

    List<MomentDBO> queryMoment(@Param("artistId") Long artistId, @Param("type") String type,
                                @Param("repostMomentId") Long repostMomentId, @Param("userIdList") List<Long> userIdList,
                                @Param("activityId") Long activityId, @Param("idList") List<Long> idList,
                                @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<MomentDBO> queryMomentByIds(@Param("artistId") Long artistId, @Param("idList") List<Long> idList);

    MomentDBO getById(@Param("artistId") Long artistId, @Param("id") Long id, @Param("status") Integer status);

    Long getUserId(@Param("artistId") Long artistId, @Param("id") Long id);

    List<MomentDBO> queryByRepostMomentId(@Param("artistId") Long artistId, @Param("repostMomentId") Long repostMomentId,
                                          @Param("start") Integer start, @Param("pageSize") Integer pageSize);

}