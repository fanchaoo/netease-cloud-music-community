package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.TopicDBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface TopicQueryMapper {
    TopicDBO selectByPrimaryKey(@Param("artistId") Long artistId, @Param("id") Long id);

    List<TopicDBO> queryTopic(@Param("artistId") Long artistId, @Param("name") String name, @Param("start") Integer start, @Param("pageSize") Integer pageSize);

    List<TopicDBO> queryByNames(@Param("artistId") Long artistId, @Param("nameSet") Set<String> nameSet);

    TopicDBO getByName(@Param("artistId") Long artistId, @Param("name") String name);

}