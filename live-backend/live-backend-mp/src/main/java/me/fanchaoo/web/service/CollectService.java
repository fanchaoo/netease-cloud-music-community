package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.MomentDTO;
import me.fanchaoo.web.request.CancelCollectRequest;
import me.fanchaoo.web.request.CollectRequest;
import me.fanchaoo.web.request.PageRequest;
import me.fanchaoo.web.response.BaseResponse;

import java.util.List;

public interface CollectService {


    void collect(CollectRequest request);

    void cancelCollect(CancelCollectRequest request);

    BaseResponse<List<MomentDTO>> queryCollectVideo(PageRequest request);

}
