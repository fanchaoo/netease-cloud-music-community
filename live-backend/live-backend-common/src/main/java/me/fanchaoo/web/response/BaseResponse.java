package me.fanchaoo.web.response;

import io.swagger.annotations.ApiModelProperty;

public class BaseResponse<T> {
    @ApiModelProperty("业务结果对象")
    private T body;

    @ApiModelProperty("状态码（0：正常，-1：系统异常，>0：业务异常）")
    private int retcode;

    @ApiModelProperty("状态码不为0时的异常描述信息")
    private String retdesc;

    @ApiModelProperty("服务器处理时长，单位ms")
    private long serverTicks;

    public T getBody() {
        return this.body;
    }

    public BaseResponse<T> setBody(T body) {
        this.body = body;
        return this;
    }

    public int getRetcode() {
        return this.retcode;
    }

    public BaseResponse<T> setRetcode(int retcode) {
        this.retcode = retcode;
        return this;
    }

    public String getRetdesc() {
        return this.retdesc;
    }

    public BaseResponse<T> setRetdesc(String retdesc) {
        this.retdesc = retdesc;
        return this;
    }

    public long getServerTicks() {
        return this.serverTicks;
    }

    public BaseResponse<T> setServerTicks(long serverTicks) {
        this.serverTicks = serverTicks;
        return this;
    }
}
