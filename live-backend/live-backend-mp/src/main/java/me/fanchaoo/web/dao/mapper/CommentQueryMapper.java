package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.CommentDBO;
import me.fanchaoo.web.dto.GroupCountDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentQueryMapper {
    CommentDBO selectByPrimaryKey(Long id);

    int exists(@Param("artistId") Long artistId, @Param("id") Long id);

    List<CommentDBO> queryComment(@Param("artistId") Long artistId, @Param("momentId") Long momentId,
                                  @Param("parentId") Long parentId, @Param("start") int start,
                                  @Param("pageSize") Integer pageSize);

    List<CommentDBO> queryCommentIds(@Param("artistId") Long artistId, @Param("commentIdList") List<Long> commentIdList);

    List<GroupCountDTO> countByParentId(@Param("artistId") Long artistId, @Param("commentIdList") List<Long> commentIdList);

    CommentDBO getById(@Param("artistId") Long artistId, @Param("id") Long id, @Param("status") Integer status);

    Long getUserId(@Param("artistId") Long artistId, @Param("id") Long id);

}