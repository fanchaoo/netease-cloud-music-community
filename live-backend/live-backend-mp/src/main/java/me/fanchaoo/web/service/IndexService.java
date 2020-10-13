package me.fanchaoo.web.service;

import me.fanchaoo.web.dto.IndexSwiperDTO;
import me.fanchaoo.web.dto.QueryGuessYouLikeDTO;
import me.fanchaoo.web.dto.QueryIndexBlockDTO;
import me.fanchaoo.web.response.BaseResponse;

import java.util.List;

public interface IndexService {

    BaseResponse<List<IndexSwiperDTO>> queryIndexSwiper();

    BaseResponse<List<QueryIndexBlockDTO>> queryIndexBlock();

    BaseResponse<List<QueryGuessYouLikeDTO>> queryGuessYouLike();

}
