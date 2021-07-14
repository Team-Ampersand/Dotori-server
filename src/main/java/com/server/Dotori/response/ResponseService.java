package com.server.Dotori.response;

import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.ListResult;
import com.server.Dotori.response.result.SingleResult;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @Getter
    public enum CommonResponse {
        SUCCESS(1, "성공하였습니다."),
        FAIL(-1, "실패하였습니다.");

        int code;
        String massage;

        CommonResponse(int code, String massage){
            this.code = code;
            this.massage = massage;
        }
    }

    // 성공/실패 응답 객체 반환
    private CommonResult setResponseResult(CommonResult result, CommonResponse commonResponse){
        result.setSuccess(commonResponse == CommonResponse.SUCCESS ? true : false);
        result.setCode(commonResponse.getCode());
        result.setMassage(commonResponse.getMassage());
        return result;
    }

    // 성공결과 객체 반환
    public CommonResult getSuccessResult() {
        return setResponseResult(new CommonResult(), CommonResponse.SUCCESS);
    }

    // 단일건의 데이터가 있는 결과 객체 반환
    public <T> SingleResult<T> getSingleResult(T data){
        return new SingleResult<T>(getSuccessResult(), data);
    }

    // 다중값의 데이터가 있는 결과 객체 반환
    public <T> ListResult<T> getListResult(List<T> list){
        return new ListResult<T>(getSuccessResult(), list);
    }

    //실패 결과 객체 반환
    public CommonResult getFailResult() {
        return setResponseResult(new CommonResult(), CommonResponse.FAIL);
    }

    //커스텀 실패결과 객체 반환
    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMassage(msg);
        return result;
    }
}
