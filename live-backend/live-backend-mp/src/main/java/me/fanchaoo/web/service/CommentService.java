package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.CommentDTO;
import me.fanchaoo.web.request.GetCommentDetailRequest;
import me.fanchaoo.web.request.PublishCommentRequest;
import me.fanchaoo.web.request.DeleteCommentRequest;
import me.fanchaoo.web.request.QueryMomentCommentRequest;
import me.fanchaoo.web.response.BaseResponse;

import java.util.List;

public interface CommentService {


    BaseResponse<Long> publishComment(PublishCommentRequest request);

    BaseResponse deleteComment(DeleteCommentRequest request);

    BaseResponse<List<CommentDTO>> queryMomentComment(QueryMomentCommentRequest request);

    BaseResponse<CommentDTO> getCommentDetail(GetCommentDetailRequest request);

}
