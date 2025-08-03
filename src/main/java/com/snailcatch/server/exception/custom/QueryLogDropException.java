package com.snailcatch.server.exception.custom;

import com.snailcatch.server.exception.ErrorCode;
import com.snailcatch.server.exception.ExceptionBase;
import org.springframework.http.HttpStatus;

public class QueryLogDropException  extends ExceptionBase {

    public QueryLogDropException() {
        this.errorCode = ErrorCode.QUERY_LOG_DROP;
        this.errorMessage = "버려지는 쿼리 발생";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}