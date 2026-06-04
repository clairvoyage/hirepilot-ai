package com.hirepilot.integration.service;

import com.hirepilot.integration.dto.HealthStatusDto;
import com.hirepilot.shared.constants.SharedConstants;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    public HealthStatusDto health() {
        return new HealthStatusDto("integration", SharedConstants.STATUS_UP, Instant.now());
    }
}
