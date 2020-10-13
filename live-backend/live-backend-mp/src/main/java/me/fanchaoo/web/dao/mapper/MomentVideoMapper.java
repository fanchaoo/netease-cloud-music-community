package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.MomentVideoDBO;
import org.apache.ibatis.annotations.Param;

public interface MomentVideoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MomentVideoDBO record);

    int insertSelective(MomentVideoDBO record);

    void updateCollectCount(@Param("id") Long id, @Param("artistId") Long artistId, @Param("count") int count);

}