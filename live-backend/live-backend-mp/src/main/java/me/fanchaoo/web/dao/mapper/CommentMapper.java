package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.CommentDBO;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CommentDBO record);

    int insertSelective(CommentDBO record);

    int updateByPrimaryKeySelective(CommentDBO record);

    int updateByPrimaryKey(CommentDBO record);

    int updateLikeCount(@Param("artistId") Long artistId, @Param("id") Long id, @Param("count") int count);

    int updataStatus(@Param("artistId") Long artistId, @Param("userId") Long userId,
                     @Param("id") Long id, @Param("status") Integer status);
}