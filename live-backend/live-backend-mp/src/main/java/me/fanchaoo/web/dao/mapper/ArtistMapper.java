package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.ArtistDBO;

public interface ArtistMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArtistDBO record);

    int insertSelective(ArtistDBO record);
    int updateByPrimaryKeySelective(ArtistDBO record);

    int updateByPrimaryKey(ArtistDBO record);
}