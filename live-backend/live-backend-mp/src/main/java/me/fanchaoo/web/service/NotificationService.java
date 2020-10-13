package me.fanchaoo.web.service;

import me.fanchaoo.dbo.NotificationDBO;
import me.fanchaoo.web.dto.AtNotificationDTO;
import me.fanchaoo.web.dto.CommentNotificationDTO;
import me.fanchaoo.web.request.ClearNotificationCountRequest;
import me.fanchaoo.web.request.PageRequest;
import me.fanchaoo.web.response.BaseResponse;

import java.util.List;

public interface NotificationService {

    void sendNotification(NotificationDBO notification);

    BaseResponse<List<AtNotificationDTO>> queryAtInfo(PageRequest request);

    BaseResponse clearNotificationCount(ClearNotificationCountRequest request);

    BaseResponse<List<CommentNotificationDTO>> queryCommentInfo(PageRequest request);

}
