package me.fanchaoo.web.dao.mapper;

import me.fanchaoo.dbo.UserDBO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    int insertSelective(UserDBO record);

    int updateByPrimaryKeySelective(UserDBO record);

    void updateFollowCount(@Param("userId") Long userId, @Param("artistId") Long artistId, @Param("count") int count);

    void updateFansCount(@Param("userId") Long userId, @Param("artistId") Long artistId, @Param("count") int count);

    void updateCommentUnread(@Param("userId") Long userId, @Param("artistId") Long artistId, @Param("count") int count);

    void clearCommentUnread(@Param("userId") Long userId, @Param("artistId") Long artistId);

    void updateAtUnread(@Param("userId") Long userId, @Param("artistId") Long artistId, @Param("count") int count);

    void clearAtUnread(@Param("userId") Long userId, @Param("artistId") Long artistId);

    void updateNotificationUnread(@Param("userId") Long userId, @Param("artistId") Long artistId, @Param("count") int count);

    void clearNotificationUnread(@Param("userId") Long userId, @Param("artistId") Long artistId);

}