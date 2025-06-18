package com.snailcatch.server.exception.custom;

import com.snailcatch.server.exception.ErrorCode;
import com.snailcatch.server.exception.ExceptionBase;
import org.springframework.http.HttpStatus;

public class QueryLogSaveException extends ExceptionBase {

    public QueryLogSaveException() {
        this.errorCode = ErrorCode.QUERY_LOG_SAVE_FAILED;
        this.errorMessage = "쿼리 저장 실패 발생 ";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}