package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.RegionProvinceDBO;

public interface RegionProvinceQueryMapper {
    RegionProvinceDBO selectByPrimaryKey(String id);
}