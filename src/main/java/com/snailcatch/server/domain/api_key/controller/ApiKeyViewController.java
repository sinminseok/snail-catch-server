package com.snailcatch.server.domain.api_key.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class ApiKeyViewController {

    @GetMapping("/api-key")
    public String showApiKeyPage() {
        return "api-key";
    }
}
