package com.romeulima.passin.controllers;

import com.romeulima.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.romeulima.passin.services.AttendeeService;
import com.romeulima.passin.services.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {
    private final AttendeeService attendeeService;
  @GetMapping("/{attendeeId}/badge")
  public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
      AttendeeBadgeResponseDTO responseBadge = this.attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
      return ResponseEntity.ok(responseBadge);
  }

  @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
      this.attendeeService.chekInAttendee(attendeeId);

      var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeId).toUri();

      return ResponseEntity.created(uri).build();
  }
}
