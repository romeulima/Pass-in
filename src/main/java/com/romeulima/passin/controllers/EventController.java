package com.romeulima.passin.controllers;

import com.romeulima.passin.domain.attendee.Attendee;
import com.romeulima.passin.dto.attendee.AttendeeDetails;
import com.romeulima.passin.dto.attendee.AttendeeIdDTO;
import com.romeulima.passin.dto.attendee.AttendeeRequestDTO;
import com.romeulima.passin.dto.attendee.AttendeesListResponseDTO;
import com.romeulima.passin.dto.event.EventIdDTO;
import com.romeulima.passin.dto.event.EventRequestDTO;
import com.romeulima.passin.dto.event.EventResponseDTO;
import com.romeulima.passin.services.AttendeeService;
import com.romeulima.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getDetail(@PathVariable String id){
        EventResponseDTO event = this.eventService.getEventDetail(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);
        var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.eventId()).toUri();
        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, body);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();
        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }


    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id){
        AttendeesListResponseDTO eventAttendeesList = this.attendeeService.getEventsAttendee(id);
        return ResponseEntity.ok(eventAttendeesList);
    }

}
