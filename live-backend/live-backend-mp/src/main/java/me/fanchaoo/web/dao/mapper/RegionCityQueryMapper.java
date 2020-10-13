package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.RegionCityDBO;

import java.util.List;

public interface RegionCityQueryMapper {
    RegionCityDBO selectByPrimaryKey(String id);

    List<RegionCityDBO> queryAll();

}