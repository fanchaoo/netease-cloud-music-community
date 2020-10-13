package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.ActivityDBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityQueryMapper {
    ActivityDBO selectByPrimaryKey(Long id);

    List<ActivityDBO> queryActivity(@Param("artistId") Long artistId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<ActivityDBO> queryActivityByIds(@Param("artistId") Long artistId, @Param("idList") List<Long> idList);

}