package com.hirepilot.search.service;

import com.hirepilot.search.dto.HealthStatusDto;
import com.hirepilot.shared.constants.SharedConstants;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class HealthService {
    public HealthStatusDto health() {
        return new HealthStatusDto("search", SharedConstants.STATUS_UP, Instant.now());
    }
}
