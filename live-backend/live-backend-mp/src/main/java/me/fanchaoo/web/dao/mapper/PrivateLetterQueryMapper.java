package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.PrivateLetterDBO;

public interface PrivateLetterQueryMapper {
    PrivateLetterDBO selectByPrimaryKey(Long id);
}