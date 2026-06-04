package com.hirepilot.auth.service;

import com.hirepilot.auth.dto.HealthStatusDto;
import com.hirepilot.shared.constants.SharedConstants;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    public HealthStatusDto health() {
        return new HealthStatusDto("auth", SharedConstants.STATUS_UP, Instant.now());
    }
}
