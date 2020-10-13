package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.TagDBO;
import org.apache.ibatis.annotations.Param;

public interface TagMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TagDBO record);

    int updateByPrimaryKeySelective(TagDBO record);

    void updateFansCount(@Param("userId") Long userId, @Param("artistId") Long artistId, @Param("count") int count);
}