package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.RepostDBO;

public interface RepostQueryMapper {
    RepostDBO selectByPrimaryKey(Long id);
}