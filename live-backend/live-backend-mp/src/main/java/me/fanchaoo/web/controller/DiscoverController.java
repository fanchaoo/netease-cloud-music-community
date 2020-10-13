package me.fanchaoo.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.fanchaoo.web.dto.MomentDTO;
import me.fanchaoo.web.request.PageRequest;
import me.fanchaoo.web.request.QuerySquareMomentRequest;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.ActivityService;
import me.fanchaoo.web.service.MomentService;
import me.fanchaoo.web.service.UserService;
import me.fanchaoo.web.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(description = "发现")
@RestController
@RequestMapping("/discover")
public class DiscoverController {

    @Autowired
    private MomentService momentService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询好友动态")
    @PostMapping(value = "/queryFriendMoment")
    public BaseResponse<List<MomentDTO>> queryFriendMoment(@Valid @RequestBody PageRequest request) {
        return momentService.queryFriendMoment(request);
    }

}
