package com.snailcatch.server.domain.query_log.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class QueryLogViewController {

    @GetMapping("/view/query-logs")
    public String showQueryLogPage() {
        return "query-logs"; // templates/query-logs.html
    }
}
