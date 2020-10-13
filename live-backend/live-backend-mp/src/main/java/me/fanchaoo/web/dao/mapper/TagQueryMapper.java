package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.TagDBO;

public interface TagQueryMapper {
    TagDBO selectByPrimaryKey(Long id);
}