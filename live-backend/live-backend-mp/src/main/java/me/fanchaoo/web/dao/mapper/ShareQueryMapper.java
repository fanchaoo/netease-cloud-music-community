package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.ShareDBO;

public interface ShareQueryMapper {
    ShareDBO selectByPrimaryKey(Long id);
}