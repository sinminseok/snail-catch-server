package com.snailcatch.server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode{
    QUERY_LOG_DROP(5001),
    QUERY_LOG_SAVE_FAILED(5002);

    private final int code;
}
