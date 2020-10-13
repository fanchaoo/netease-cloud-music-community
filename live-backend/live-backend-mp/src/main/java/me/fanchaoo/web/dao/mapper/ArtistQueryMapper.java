package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.ArtistDBO;

public interface ArtistQueryMapper {
    ArtistDBO selectByPrimaryKey(Long id);

    ArtistDBO getByRandomKey(String randomKey);

}