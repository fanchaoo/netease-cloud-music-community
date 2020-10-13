package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.JoinedActivityDBO;

public interface JoinedActivityMapper {

    int insertSelective(JoinedActivityDBO record);

    int updateByPrimaryKeySelective(JoinedActivityDBO record);

    void delete(JoinedActivityDBO dbo);

}