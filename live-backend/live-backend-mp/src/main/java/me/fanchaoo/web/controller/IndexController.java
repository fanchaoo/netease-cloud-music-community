package me.fanchaoo.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.fanchaoo.web.dto.IndexSwiperDTO;
import me.fanchaoo.web.dto.QueryGuessYouLikeDTO;
import me.fanchaoo.web.dto.QueryIndexBlockDTO;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.ActivityService;
import me.fanchaoo.web.service.IndexService;
import me.fanchaoo.web.service.MomentService;
import me.fanchaoo.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "首页")
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private MomentService momentService;

    @Autowired
    private IndexService indexService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询首页轮播图")
    @PostMapping(value = "/queryIndexSwiper")
    public BaseResponse<List<IndexSwiperDTO>> queryIndexSwiper() {
        return indexService.queryIndexSwiper();
    }

    @ApiOperation(value = "查询首页板块")
    @PostMapping(value = "/queryIndexBlock")
    public BaseResponse<List<QueryIndexBlockDTO>> queryIndexBlock() {
        return indexService.queryIndexBlock();
    }

    @ApiOperation(value = "查询首页板块")
    @PostMapping(value = "/queryGuessYouLike")
    public BaseResponse<List<QueryGuessYouLikeDTO>> queryGuessYouLike() {
        return indexService.queryGuessYouLike();
    }

}
