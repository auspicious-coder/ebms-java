package com.ebms.domain.event.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.ebms.api.event.dto.EventRequest;
import com.ebms.api.event.dto.EventResponse;
import com.ebms.common.response.ApiResponse;
import com.ebms.common.util.ResponseUtils;
import com.ebms.domain.client.entity.Client;
import com.ebms.domain.client.repository.ClientRepository;
import com.ebms.domain.event.entity.Event;
import com.ebms.domain.event.repository.EventRepository;
import com.ebms.domain.event.service.EventService;

public class EventServiceImpl implements EventService{

    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private EventRepository eventRepo;

    @Override
    public ApiResponse<EventResponse> addEvent(EventRequest eventRequest) {
        try {
            Event event = new Event();
            event.setClientId(
                eventRequest.getClientId() == null
                    ? null
                    : clientRepo.findById(eventRequest.getClientId()).orElse(null)
            );
            event.setEventName(eventRequest.getEventName());
            event.setStartDate(eventRequest.getStartDate());
            event.setEndDate(eventRequest.getEndDate());
            event.setStatus("y");
            event.setCreatedAt(LocalDateTime.now());
            return ResponseUtils.success(mapEventResponse(eventRepo.save(event)), "Event added successfully");
        } catch (Exception e) {
            return ResponseUtils.failure("Unable to add event", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<EventResponse> updateEvent(Long eventId, EventRequest eventRequest) {
        try {
            Optional<Event> eventOptional = eventRepo.findById(eventId);
            if(eventOptional.isPresent()){
                Event event = new Event();
                event.setClientId(
                eventRequest.getClientId() == null
                    ? null
                    : clientRepo.findById(eventRequest.getClientId()).orElse(null)
                );
                event.setEventName(eventRequest.getEventName());
                event.setStartDate(eventRequest.getStartDate());
                event.setEndDate(eventRequest.getEndDate());
                event.setUpdatedAt(LocalDateTime.now());
                return ResponseUtils.success(mapEventResponse(eventRepo.save(event)), "Event updated successfully");
            } else{
                return ResponseUtils.failure("Unable to find event_id", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseUtils.failure("Unable to update event", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<EventResponse> updateEventStatus(Long eventId, String status) {
        try {
            Optional<Event> eventOptional = eventRepo.findById(eventId);
            if(eventOptional.isPresent()){
                Event event = new Event();
                if(status.equalsIgnoreCase("y") || status.equalsIgnoreCase("n")){
                    event.setStatus(status); 
                } else{
                    return ResponseUtils.failure("Use either y or n", HttpStatus.BAD_REQUEST);
                }
                event.setUpdatedAt(LocalDateTime.now());
                return ResponseUtils.success(mapEventResponse(eventRepo.save(event)), "Event status updated successfully");
            } else{
                return ResponseUtils.failure("Unable to find event_id", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseUtils.failure("Unable to change event status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<EventResponse> getByEventId(Long eventId) {
        try {
            Optional<Event> event = eventRepo.findById(eventId);
            if(event.isPresent()){
                Event eventobj = event.get();
                return ResponseUtils.success(mapEventResponse(eventobj), "success");
            } else{
                return ResponseUtils.failure("Unable to find event_id", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseUtils.failure("Unable to get event", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ApiResponse<List<EventResponse>> getAllEvents(int flag) {
        try {
            List<Event> event;
            if(flag == 1){
                event = eventRepo.findByStatusOrderByEventNameAsc("y");
            } else if(flag == 0){
                event = eventRepo.findAllByOrderByStatusDescCreatedAtDesc();
            } else{
                return ResponseUtils.failure("Either use  1 or 0", HttpStatus.BAD_REQUEST);
            }
            List<EventResponse> events = event.stream()
                    .map(this::mapEventResponse)
                    .collect(Collectors.toList());
            return ResponseUtils.success(events, "success");
        } catch (Exception e) {
            return ResponseUtils.failure("Unable to get events", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private EventResponse mapEventResponse(Event event){
        EventResponse eventResponse = new EventResponse();
        eventResponse.setId(event.getEventId());
        eventResponse.setClientId(event.getClientId().getClientId());
        eventResponse.setClientName(event.getClientId().getClientName());
        eventResponse.setEventName(event.getEventName());
        eventResponse.setStartDate(event.getStartDate());
        eventResponse.setEndDate(event.getEndDate());
        eventResponse.setStatus(event.getStatus());
        eventResponse.setCreatedAt(event.getCreatedAt());
        eventResponse.setUpdatedAt(event.getUpdatedAt());
        return eventResponse;
    }
}
