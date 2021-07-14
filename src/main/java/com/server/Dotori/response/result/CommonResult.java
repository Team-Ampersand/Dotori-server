package com.server.Dotori.response.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class CommonResult {

    @ApiModelProperty("응답 성공여부")
    private boolean success;

    @ApiModelProperty("응답 코드 번호")
    private int code;

    @ApiModelProperty("응답 메시지")
    private String massage;

    public CommonResult(CommonResult commonResult){
        this.success = commonResult.success;
        this.code = commonResult.code;
        this.massage = commonResult.massage;
    }
}
