package me.fanchaoo.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.constant.RedisKey;
import me.fanchaoo.web.dto.IndexSwiperDTO;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.IndexService;
import me.fanchaoo.web.util.RedisUtils;
import me.fanchaoo.util.UserContext;
import me.fanchaoo.web.dto.QueryGuessYouLikeDTO;
import me.fanchaoo.web.dto.QueryIndexBlockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public BaseResponse<List<IndexSwiperDTO>> queryIndexSwiper() {
        BaseResponse<List<IndexSwiperDTO>> response = new BaseResponse<>();
        List<IndexSwiperDTO> list = redisUtils.getList(UserContext.getRedisKeyPrefix() + RedisKey.INDEX_SWIPER, IndexSwiperDTO.class);
        response.setBody(list);
        return response;
    }

    @Override
    public BaseResponse<List<QueryIndexBlockDTO>> queryIndexBlock() {
        return null;
    }

    @Override
    public BaseResponse<List<QueryGuessYouLikeDTO>> queryGuessYouLike() {
        return null;
    }
}
