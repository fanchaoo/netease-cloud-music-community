package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.TagRelationDBO;

import java.util.List;

public interface TagRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TagRelationDBO record);

    int insertSelective(TagRelationDBO record);
    int updateByPrimaryKeySelective(TagRelationDBO record);

    int updateByPrimaryKey(TagRelationDBO record);

    void insertBatch(List<TagRelationDBO> tagRelationDBOList);
}