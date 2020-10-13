package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.MomentDBO;
import org.apache.ibatis.annotations.Param;

public interface MomentMapper {
    int deleteByPrimaryKey(@Param("id") Long id, @Param("userId") Long userId, @Param("artistId") Long artistId);

    int insertSelective(MomentDBO record);

    int updateByPrimaryKeySelective(MomentDBO record);

    int updateLikeCount(@Param("artistId") Long artistId, @Param("id") Long id, @Param("count") int count);

    int updateCommentCount(@Param("artistId") Long artistId, @Param("id") Long id, @Param("count") int count);

    int updateRepostCount(@Param("artistId") Long artistId, @Param("id") Long id, @Param("count") int count);

    int updateShareCount(@Param("artistId") Long artistId, @Param("id") Long id, @Param("count") int count);

    int updateViewCount(@Param("artistId") Long artistId, @Param("id") Long id, @Param("count") int count);

}