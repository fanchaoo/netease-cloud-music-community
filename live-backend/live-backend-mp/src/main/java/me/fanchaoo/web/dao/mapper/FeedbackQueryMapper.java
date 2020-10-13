package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.FeedbackDBO;

public interface FeedbackQueryMapper {
    FeedbackDBO selectByPrimaryKey(Long id);
}