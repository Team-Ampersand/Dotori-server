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
    public enum CommonResponse{
        SUCCESS(1, "성공하였습니다."),
        FAIL(-1, "실패하였습니다.");

        int code;
        String massage;

        CommonResponse(int code, String massage){
            this.code = code;
            this.massage = massage;
        }
    }

    /**
     * 요청 데이터가 없는 성공 결과 객체 반환
     * @return CommonResult - 성공 결과를 가지고 있는 CommonResult객체
     * @author 정시원
     */
    public CommonResult getSuccessResult() {
        return CommonResult.builder()
                .success(true)
                .code(CommonResponse.SUCCESS.getCode())
                .massage(CommonResponse.SUCCESS.getMassage())
                .build();
    }

    /**
     * 단일건의 데이터가 있는 결과 객체 반환
     * @param data 단일 데이터
     * @param <T> 단일 데이터 타입
     * @return SingleResult - 단일건의 데이터가 있는 결과객체
     * @author 정시원
     */
    public <T> SingleResult<T> getSingleResult(T data){
        return new SingleResult<T>(getSuccessResult(), data);
    }

    /**
     * 다중값의 데이터가 있는 결과 객체 반환
     * @param list 다중 데이터
     * @param <T> 다중 데이터 타입
     * @return SingleResult - 다중값의 데이터가 있는 결과객체
     * @author 정시원
     */
    public <T> ListResult<T> getListResult(List<T> list){
        return new ListResult<T>(getSuccessResult(), list);
    }

    /**
     * 사용자 지정 실패 결과 객체 반환
     * @param code 반환할 code
     * @param msg 반환할 message
     * @return CommonResult - 실패결과 객체
     * @author 정시원
     */
    public CommonResult getFailResult(int code, String msg) {
        return CommonResult.builder()
                .success(false)
                .code(code)
                .massage(msg)
                .build();
    }
}
