package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.FollowRelationDBO;

public interface FollowRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FollowRelationDBO record);

    int insertSelective(FollowRelationDBO record);

    int updateByPrimaryKeySelective(FollowRelationDBO record);

    int updateByPrimaryKey(FollowRelationDBO record);

    void delete(FollowRelationDBO followRelationDBO);
}