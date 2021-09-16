package com.server.Dotori.exception.customError;

import com.server.Dotori.exception.customError.exception.CustomForbiddenException;
import com.server.Dotori.exception.customError.exception.CustomNotFoundException;
import com.server.Dotori.exception.customError.exception.CustomUnauthorizedException;
import com.server.Dotori.exception.response.CustomException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@RestController
public class BasicErrorController implements ErrorController {

    @GetMapping("/error")
    public void handleError(HttpServletRequest req)  {
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null){
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == NOT_FOUND.value()) throw new CustomNotFoundException();
            else if(statusCode == FORBIDDEN.value()) throw new CustomForbiddenException();
            else if(statusCode == UNAUTHORIZED.value()) throw new CustomUnauthorizedException();

            throw new CustomException("basic error처리중 알 수 없는 에러가 발생해 error handling을 하지 못했습니다", INTERNAL_SERVER_ERROR);
        }
    }
}
