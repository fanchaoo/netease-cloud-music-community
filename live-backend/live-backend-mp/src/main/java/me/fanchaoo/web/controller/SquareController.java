package me.fanchaoo.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.fanchaoo.web.dto.MomentDTO;
import me.fanchaoo.web.dto.MomentImageDTO;
import me.fanchaoo.web.request.QuerySquareImageRequest;
import me.fanchaoo.web.request.QuerySquareMomentRequest;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.ActivityService;
import me.fanchaoo.web.service.MomentService;
import me.fanchaoo.web.service.UserService;
import me.fanchaoo.web.service.VideoService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Api(description = "广场")
@RestController
@RequestMapping("/square")
public class SquareController {

    @Autowired
    private MomentService momentService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询广场动态")
    @PostMapping(value = "/querySquareMoment")
    public BaseResponse<List<MomentDTO>> querySquareMoment(@Valid @RequestBody QuerySquareMomentRequest request) {
        return momentService.querySquareMoment(request);
    }

    @ApiOperation(value = "查询广场相册")
    @PostMapping(value = "/querySquareImage")
    public BaseResponse<TreeMap<String, List<MomentImageDTO>>> querySquareImage(@Valid @RequestBody QuerySquareImageRequest request) {
        return momentService.querySquareImage(request);
    }
}
