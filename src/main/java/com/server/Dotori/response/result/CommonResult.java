package com.server.Dotori.response.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE) @AllArgsConstructor
public class CommonResult {

    @ApiModelProperty("응답 성공여부")
    private boolean success;

    @ApiModelProperty("응답 코드 번호")
    private int code;

    @ApiModelProperty("응답 메시지")
    private String massage;

    public void updateMassage(String massage){
        this.massage = massage;
    }

    public CommonResult(CommonResult commonResult){
        this.success = commonResult.success;
        this.code = commonResult.code;
        this.massage = commonResult.massage;
    }
}
