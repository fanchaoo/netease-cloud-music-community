package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.RegionProvinceDBO;

public interface RegionProvinceMapper {
    int deleteByPrimaryKey(String id);

    int insert(RegionProvinceDBO record);

    int insertSelective(RegionProvinceDBO record);
    int updateByPrimaryKeySelective(RegionProvinceDBO record);

    int updateByPrimaryKey(RegionProvinceDBO record);
}