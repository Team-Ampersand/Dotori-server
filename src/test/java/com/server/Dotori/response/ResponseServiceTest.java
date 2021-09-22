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

@Slf4j
@SpringBootTest
class ResponseServiceTest {

    @Autowired ResponseService responseService;

    final String SUCCESS_MSG = ResponseService.CommonResponse.SUCCESS.massage;
    final int SUCCESS_CODE = ResponseService.CommonResponse.SUCCESS.code;

    final String FAIL_MSG = ResponseService.CommonResponse.FAIL.massage;
    final int FAIL_CODE = ResponseService.CommonResponse.FAIL.code;

    @Test @DisplayName("ResponseService.CommonResponse 성공시 message, code 검증")
    void CommonResponse_성공_message_code_검증(){
        assertEquals("성공하였습니다.", SUCCESS_MSG);
        assertEquals(1, SUCCESS_CODE);
    }

    @Test @DisplayName("\"ResponseService.CommonResponse 실패시 message, code 검증")
    void CommonResponse_실패_message_code_검증(){
        assertEquals("실패하였습니다.", FAIL_MSG);
        assertEquals(-1, FAIL_CODE);
    }

    @Test @DisplayName("getSuccessResult 테스트")
    void getSuccessResult_테스트(){
        // Given When
        CommonResult successResult = responseService.getSuccessResult();

        // Then
        assertEquals(true, successResult.isSuccess());
        assertEquals(SUCCESS_MSG, successResult.getMassage());
        assertEquals(SUCCESS_CODE, successResult.getCode());
    }

    @Test @DisplayName("getSingleResult 테스트")
    void getSingleResult_테스트(){
        //Given
        String givenData = "정시원";

        //When
        SingleResult<String> singleResult = responseService.getSingleResult(givenData);

        //Then
        assertEquals(true, singleResult.isSuccess());
        assertEquals(SUCCESS_CODE, singleResult.getCode());
        assertEquals(SUCCESS_MSG, singleResult.getMassage());
        assertEquals(givenData, singleResult.getData());

        log.debug("SingleResult = {}", singleResult.getData());
    }

    @Test @DisplayName("getListResult 테스트")
    void getListResult_테스트(){
        //Given
        List<String> givenData = List.of(new String[]{"배태현, 김태민, 노경준"});

        //When
        ListResult<String> listResult = responseService.getListResult(givenData);

        //Then
        assertEquals(true, listResult.isSuccess());
        assertEquals(SUCCESS_CODE, listResult.getCode());
        assertEquals(SUCCESS_MSG, listResult.getMassage());
        assertEquals(givenData, listResult.getList());

        log.debug("ListResult = {}", listResult.getList());
    }
}
