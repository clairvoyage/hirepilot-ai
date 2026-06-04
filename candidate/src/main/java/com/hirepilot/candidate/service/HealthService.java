package com.hirepilot.candidate.service;

import com.hirepilot.candidate.dto.HealthStatusDto;
import com.hirepilot.shared.constants.SharedConstants;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    public HealthStatusDto health() {
        return new HealthStatusDto("candidate", SharedConstants.STATUS_UP, Instant.now());
    }
}
