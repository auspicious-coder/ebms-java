package com.ebms.api.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EventRequest {
    private Long clientId;
    private String eventName;
    private LocalDate startDate;
    private LocalDate endDate;
}
