package com.hirepilot.jobs.service;

import com.hirepilot.jobs.dto.HealthStatusDto;
import com.hirepilot.shared.constants.SharedConstants;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    public HealthStatusDto health() {
        return new HealthStatusDto("jobs", SharedConstants.STATUS_UP, Instant.now());
    }
}
