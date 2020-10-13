package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.MomentVideoDBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MomentVideoQueryMapper {
    MomentVideoDBO selectByPrimaryKey(@Param("artistId") Long artistId, @Param("id") Long id);

    List<MomentVideoDBO> queryByMomentIds(@Param("artistId") Long artistId, @Param("momentIdList") List<Long> momentIdList);

    List<MomentVideoDBO> queryByIds(@Param("artistId") Long artistId, @Param("idList") List<Long> idList);

}