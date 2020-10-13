package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.UserDBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserQueryMapper {
    UserDBO getById(@Param("artistId") Long artistId, @Param("id") Long id);

    UserDBO getByMiniWeixinUnionId(@Param("artistId") Long artistId, @Param("unionId") String unionId, @Param("openId") String openId);

    UserDBO getByMiniQQUnionId(@Param("artistId") Long artistId, @Param("unionId") String unionId, @Param("openId") String openId);

    List<UserDBO> queryByUserIds(@Param("artistId") Long artistId, @Param("userIdList") List<Long> userIdList);

    List<UserDBO> queryByNames(@Param("artistId") Long artistId, @Param("nameSet") Set<String> nameSet);

}