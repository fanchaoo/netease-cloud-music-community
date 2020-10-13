package me.fanchaoo.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.web.response.BaseResponse;
import me.fanchaoo.web.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Api(description = "文件")
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/uploadImageOrVideo")
    public BaseResponse<String> uploadImageOrVideo(MultipartFile file) throws Exception {
        log.info("接收文件完毕");
        return new BaseResponse<String>().setBody(ossService.uploadImageOrVideo(file));
    }


}
