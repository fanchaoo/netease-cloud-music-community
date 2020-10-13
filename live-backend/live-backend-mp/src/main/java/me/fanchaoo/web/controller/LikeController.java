package me.fanchaoo.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.fanchaoo.web.dto.RepostUserDTO;
import me.fanchaoo.web.dto.UserDTO;
import me.fanchaoo.web.request.CancelLikeRequest;
import me.fanchaoo.web.request.LikeRequest;
import me.fanchaoo.web.request.QueryLikeUserRequest;
import me.fanchaoo.web.request.QueryRepostUserRequest;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "点赞")
@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @ApiOperation(value = "点赞")
    @PostMapping(value = "/like")
    public BaseResponse like(@Valid @RequestBody LikeRequest request) {

        likeService.like(request);
        return new BaseResponse();
    }

    @ApiOperation(value = "取消点赞")
    @PostMapping(value = "/cancelLike")
    public BaseResponse cancelLike(@Valid @RequestBody CancelLikeRequest request) {

        likeService.cancelLike(request);
        return new BaseResponse();
    }

    @ApiOperation(value = "查询点赞用户")
    @PostMapping(value = "/queryLikeUser")
    public BaseResponse<List<UserDTO>> queryLikeUser(@Valid @RequestBody QueryLikeUserRequest request) {

        return likeService.queryLikeUser(request);
    }

}
