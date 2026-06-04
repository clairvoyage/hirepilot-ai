package com.hirepilot.employer.controller;

import com.hirepilot.employer.dto.HealthStatusDto;
import com.hirepilot.employer.service.HealthService;
import com.hirepilot.shared.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employer")
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
