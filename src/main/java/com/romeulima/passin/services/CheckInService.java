package com.romeulima.passin.services;

import com.romeulima.passin.domain.attendee.Attendee;
import com.romeulima.passin.domain.checkin.CheckIn;
import com.romeulima.passin.domain.checkin.exceptions.CheckInAlreadyExistsException;
import com.romeulima.passin.repositories.CheckinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {
    private final CheckinRepository checkinRepository;

    public void registerCheckIn(Attendee attendee){

        this.verifyCheckInExists(attendee.getId());

        CheckIn newCheckIn = new CheckIn();
        newCheckIn.setAttendee(attendee);
        newCheckIn.setCreatedAt(LocalDateTime.now());

        this.checkinRepository.save(newCheckIn);
    }

    private void verifyCheckInExists(String attendeeId){
        Optional<CheckIn> isCheckedIn = this.getCheckIn(attendeeId);

        if(isCheckedIn.isPresent()) throw new CheckInAlreadyExistsException("Attendee is already checked in");
    }

    public Optional<CheckIn> getCheckIn(String attendeeId){
        return this.checkinRepository.findByAttendeeId(attendeeId);
    }
}
