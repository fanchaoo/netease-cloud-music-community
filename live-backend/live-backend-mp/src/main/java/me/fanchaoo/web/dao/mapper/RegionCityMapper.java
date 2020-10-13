package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.RegionCityDBO;

public interface RegionCityMapper {
    int deleteByPrimaryKey(String id);

    int insert(RegionCityDBO record);

    int insertSelective(RegionCityDBO record);
    int updateByPrimaryKeySelective(RegionCityDBO record);

    int updateByPrimaryKey(RegionCityDBO record);
}