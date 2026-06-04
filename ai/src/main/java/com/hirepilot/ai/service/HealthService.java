package com.hirepilot.ai.service;

import com.hirepilot.ai.dto.HealthStatusDto;
import com.hirepilot.shared.constants.SharedConstants;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    public HealthStatusDto health() {
        return new HealthStatusDto("ai", SharedConstants.STATUS_UP, Instant.now());
    }
}
