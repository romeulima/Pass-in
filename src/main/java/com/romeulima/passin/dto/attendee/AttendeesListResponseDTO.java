package com.romeulima.passin.dto.attendee;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public record AttendeesListResponseDTO(List<AttendeeDetails> attendeeDetails) {

}
