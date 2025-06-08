package com.snailcatch.server.domain.query_log.controller;

import com.snailcatch.server.global.annotation.ApiKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class QueryLogViewController {

    @GetMapping("/view/query-logs")
    public String showQueryLogPage(@ApiKey String apiKey) {
        return "query-logs";
    }
}
