package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.TopicRelationDBO;

import java.util.List;

public interface TopicRelationMapper {

    void insertBatch(List<TopicRelationDBO> dboList);

}