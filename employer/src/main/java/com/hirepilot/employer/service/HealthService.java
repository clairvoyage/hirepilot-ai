package com.hirepilot.employer.service;

import com.hirepilot.employer.dto.HealthStatusDto;
import com.hirepilot.shared.constants.SharedConstants;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    public HealthStatusDto health() {
        return new HealthStatusDto("employer", SharedConstants.STATUS_UP, Instant.now());
    }
}
