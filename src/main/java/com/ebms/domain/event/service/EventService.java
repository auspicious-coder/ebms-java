package com.ebms.domain.event.service;

import java.util.List;

import com.ebms.api.event.dto.EventRequest;
import com.ebms.api.event.dto.EventResponse;
import com.ebms.common.response.ApiResponse;

public interface EventService {
    ApiResponse<EventResponse> addEvent(EventRequest eventRequest);
    ApiResponse<EventResponse> updateEvent(Long eventId, EventRequest eventRequest);
    ApiResponse<EventResponse> updateEventStatus(Long eventId, String status);
    ApiResponse<EventResponse> getByEventId(Long eventId);
    ApiResponse<List<EventResponse>> getAllEvents(int flag);
}
