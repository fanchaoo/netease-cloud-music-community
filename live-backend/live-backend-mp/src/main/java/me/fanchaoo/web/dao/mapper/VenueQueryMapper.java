package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.VenueDBO;

public interface VenueQueryMapper {
    VenueDBO selectByPrimaryKey(Long id);
}