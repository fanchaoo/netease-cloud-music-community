package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.TopicDBO;

import java.util.List;

public interface TopicMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TopicDBO record);

    int insertSelective(TopicDBO record);

    int updateByPrimaryKeySelective(TopicDBO record);

    int updateByPrimaryKey(TopicDBO record);

    void insertBatch(List<TopicDBO> dboList);

}