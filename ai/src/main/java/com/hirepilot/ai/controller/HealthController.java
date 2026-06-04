package com.hirepilot.ai.controller;

import com.hirepilot.ai.dto.HealthStatusDto;
import com.hirepilot.ai.service.HealthService;
import com.hirepilot.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class HealthController {
    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/health")
    public ApiResponse<HealthStatusDto> health() {
        return ApiResponse.ok(healthService.health());
    }
}
