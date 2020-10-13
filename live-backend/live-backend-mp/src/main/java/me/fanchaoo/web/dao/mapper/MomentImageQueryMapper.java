package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.MomentImageDBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MomentImageQueryMapper {
    MomentImageDBO selectByPrimaryKey(Long id);

    List<MomentImageDBO> queryByMomentIds(@Param("artistId") Long artistId, @Param("momentIdList") List<Long> momentIdList);

    List<MomentImageDBO> queryImage(@Param("artistId") Long artistId, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

}