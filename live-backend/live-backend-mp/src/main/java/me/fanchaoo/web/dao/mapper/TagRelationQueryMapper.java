package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.TagRelationDBO;

public interface TagRelationQueryMapper {
    TagRelationDBO selectByPrimaryKey(Long id);
}