package com.ebms.api.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebms.api.event.dto.EventRequest;
import com.ebms.api.event.dto.EventResponse;
import com.ebms.common.response.ApiResponse;
import com.ebms.domain.event.service.EventService;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Event Controller")
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<EventResponse>> createEvent(@RequestBody EventRequest eventRequest){
        return new ResponseEntity<>(eventService.addEvent(eventRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity<ApiResponse<EventResponse>> updateEvent(@PathVariable Long eventId, @RequestBody EventRequest eventRequest){
        return new ResponseEntity<>(eventService.updateEvent(eventId,eventRequest), HttpStatus.OK);
    }

    @PutMapping("/update-status/{eventId}")
    public ResponseEntity<ApiResponse<EventResponse>> updateEventStatus(@PathVariable Long eventId, @RequestParam String status){
        return new ResponseEntity<>(eventService.updateEventStatus(eventId, status), HttpStatus.OK);
    }

    @GetMapping("/get-event/{eventId}")
    public ResponseEntity<ApiResponse<EventResponse>> getEvent(@PathVariable Long eventId){
        return new ResponseEntity<>(eventService.getByEventId(eventId), HttpStatus.OK);
    }

    @GetMapping("/get-All-events/{flag}")
    public ResponseEntity<ApiResponse<List<EventResponse>>> getAllEvents(@PathVariable int flag){
        return new ResponseEntity<>(eventService.getAllEvents(flag), HttpStatus.OK);
    }

}
