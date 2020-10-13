package me.fanchaoo.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.fanchaoo.web.dto.MomentDetailDTO;
import me.fanchaoo.web.request.DeleteMomentRequest;
import me.fanchaoo.web.request.GetMomentDetailRequest;
import me.fanchaoo.web.request.PublishMomentRequest;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "动态")
@RestController
@RequestMapping("/moment")
public class MomentController {

    @Autowired
    private MomentService momentService;

    @ApiOperation(value = "获取动态详情")
    @PostMapping(value = "/getMomentDetail")
    public BaseResponse<MomentDetailDTO> getMomentDetail(@Valid @RequestBody GetMomentDetailRequest request) {

        return momentService.getMomentDetail(request);
    }

    @ApiOperation(value = "发布动态")
    @PostMapping(value = "/publishMoment")
    public BaseResponse publishMoment(@Valid @RequestBody PublishMomentRequest request) {

        momentService.publishMoment(request);

        return new BaseResponse();
    }

    @ApiOperation(value = "删除动态")
    @PostMapping(value = "/deleteMoment")
    public BaseResponse deleteMoment(@Valid @RequestBody DeleteMomentRequest request) {

        momentService.deleteMoment(request);
        return new BaseResponse();
    }


}
