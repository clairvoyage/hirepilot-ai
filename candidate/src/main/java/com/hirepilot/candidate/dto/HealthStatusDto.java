package com.hirepilot.candidate.dto;

import java.time.Instant;

public record HealthStatusDto(String module, String status, Instant checkedAt) {
}
