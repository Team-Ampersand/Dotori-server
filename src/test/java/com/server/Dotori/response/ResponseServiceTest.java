package com.server.Dotori.response;

import com.server.Dotori.response.result.CommonResult;
import com.server.Dotori.response.result.ListResult;
import com.server.Dotori.response.result.SingleResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ResponseServiceTest {

    @Autowired
    private ResponseService responseService;

    final String SUCCESS_MSG = ResponseService.CommonResponse.SUCCESS.massage;
    final int SUCCESS_CODE = ResponseService.CommonResponse.SUCCESS.code;

    final String FAIL_MSG = ResponseService.CommonResponse.FAIL.massage;
    final int FAIL_CODE = ResponseService.CommonResponse.FAIL.code;

    @Test
    @DisplayName("CommonResponse success message, code verification")
    void CommonResponse_success_message_code_verification(){
        assertEquals("성공하였습니다.", SUCCESS_MSG);
        assertEquals(1, SUCCESS_CODE);
    }

    @Test
    @DisplayName("CommonResponse failed message, code verification")
    void CommonResponse_failed_message_code_verification(){
        assertEquals("실패하였습니다.", FAIL_MSG);
        assertEquals(-1, FAIL_CODE);
    }

    @Test
    @DisplayName("getSuccessResult Test")
    void getSuccessResult_test(){
        //given, when
        CommonResult successResult = responseService.getSuccessResult();

        //then
        assertEquals(true, successResult.isSuccess());
        assertEquals(SUCCESS_MSG, successResult.getMassage());
        assertEquals(SUCCESS_CODE, successResult.getCode());
    }

    @Test
    @DisplayName("getFailResult Test")
    void getFailResult_test(){
        //given, when
        CommonResult successResult = responseService.getFailResult();

        //then
        assertEquals(false, successResult.isSuccess());
        assertEquals(FAIL_MSG, successResult.getMassage());
        assertEquals(FAIL_CODE, successResult.getCode());
    }

    @Test
    @DisplayName("getSingleResult Test")
    void getSingleResult_test(){
        //given
        String givenData = "배태현";

        //when
        SingleResult<String> singleResult = responseService.getSingleResult(givenData);

        //then
        assertEquals(true, singleResult.isSuccess());
        assertEquals(SUCCESS_CODE, singleResult.getCode());
        assertEquals(SUCCESS_MSG, singleResult.getMassage());
        assertEquals(givenData, singleResult.getData());
    }

    @Test
    @DisplayName("getListResult Test")
    void getListResult_test(){
        //given
        List<String> givenData = List.of(new String[]{"배태현, 노경준, 김태민"});

        //when
        ListResult<String> listResult = responseService.getListResult(givenData);

        //then
        assertEquals(true, listResult.isSuccess());
        assertEquals(SUCCESS_CODE, listResult.getCode());
        assertEquals(SUCCESS_MSG, listResult.getMassage());
        assertEquals(givenData, listResult.getList());
    }

}
