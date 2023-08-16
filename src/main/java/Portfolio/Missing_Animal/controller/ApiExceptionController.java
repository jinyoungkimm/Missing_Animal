package Portfolio.Missing_Animal.controller;


import Portfolio.Missing_Animal.exception.ApiErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class) // @RestController가 붙은 모든 컨트롤러에 적용!
public class ApiExceptionController {


    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 에러
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiErrorResult illegalExHandle(IllegalArgumentException e) {

        log.error("[exceptionHandle] ex", e);

        return new ApiErrorResult("BAD", e.getMessage());

    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 에러
    @ExceptionHandler
    public ApiErrorResult exHandle(Exception e) { // Exception 예외는 물론, 그 자식 예외들까지 다 잡는다.

        log.error("[exceptionHandle] ex", e);

        return new ApiErrorResult("EX", "내부 오류");

    }





}
