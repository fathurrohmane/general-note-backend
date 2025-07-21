package com.elkusnandi.generalnote.service.impl;

import com.elkusnandi.generalnote.entity.TravelBooking;
import com.elkusnandi.generalnote.entity.TravelSchedule;
import com.elkusnandi.generalnote.entity.Users;
import com.elkusnandi.generalnote.exception.UserFaultException;
import com.elkusnandi.generalnote.mapper.TravelBookingMapper;
import com.elkusnandi.generalnote.repository.TravelBookingRepository;
import com.elkusnandi.generalnote.repository.TravelScheduleRepository;
import com.elkusnandi.generalnote.repository.UserRepository;
import com.elkusnandi.generalnote.request.TravelBookingRequest;
import com.elkusnandi.generalnote.response.TravelBookingResponse;
import com.elkusnandi.generalnote.service.TravelBookingService;
import com.elkusnandi.generalnote.util.DateTimeUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TravelBookingServiceImpl implements TravelBookingService {

    private final TravelBookingRepository travelBookingRepository;

    private final TravelScheduleRepository travelScheduleRepository;

    private final UserRepository userRepository;

    @Autowired
    public TravelBookingServiceImpl(
            TravelBookingRepository travelBookingRepository,
            TravelScheduleRepository travelScheduleRepository,
            UserRepository userRepository
    ) {
        this.travelBookingRepository = travelBookingRepository;
        this.travelScheduleRepository = travelScheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TravelBookingResponse> getBookingByUser(UUID userId) {
        return travelBookingRepository.findByCustomerId(userId).stream()
                .map(TravelBookingMapper.INSTANCE::entityToResponse).toList();
    }

    @Override
    public List<TravelBookingResponse> getBookingByScheduleId(UUID scheduleId) {
        return travelBookingRepository.findByScheduleId(scheduleId).stream()
                .map(TravelBookingMapper.INSTANCE::entityToResponse).toList();
    }

    @Override
    @Transactional
    public TravelBookingResponse createBooking(TravelBookingRequest request) {
        // Check for date time
        TravelSchedule currentTravelSchedule =
                travelScheduleRepository.findById(request.getScheduleId()).orElseThrow(() -> new UserFaultException(
                        HttpStatus.BAD_REQUEST, "Schedule not found"));

        LocalDate currentDate = DateTimeUtil.INSTANCE.getCurrentLocalDate();
        LocalTime currentTime = DateTimeUtil.INSTANCE.getCurrentLocalTime();

        if (currentDate.isAfter(currentTravelSchedule.getDate()) || currentTime.isAfter(currentTravelSchedule.getTime())) {
            throw new UserFaultException(HttpStatus.BAD_REQUEST, "Schedule already passed");
        }

        // Check for seat number validity
        // todo later
        // Check for seat availability
        Optional<TravelBooking> travelBooking = travelBookingRepository.findByScheduleIdAndSeatNumberAndStatusIn(
                request.getScheduleId(), request.getSeatNumber(),
                Arrays.asList("booked", "pending")
        );

        if (travelBooking.isPresent()) {
            throw new UserFaultException(HttpStatus.BAD_REQUEST, "Seat not available for this schedule");
        }

        Users currentUsers = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserFaultException(
                HttpStatus.BAD_REQUEST, "User not found"));

        return TravelBookingMapper.INSTANCE.entityToResponse(travelBookingRepository.save(
                new TravelBooking(
                        UUID.randomUUID(),
                        currentUsers,
                        currentTravelSchedule,
                        request.getSeatNumber(),
                        "pending"
                )
        ));
    }

    @Override
    public void cancelBooking(UUID travelBookingId) {
        TravelBooking currentTravelBooking =
                travelBookingRepository.findById(travelBookingId).orElseThrow(() -> new UserFaultException(
                        HttpStatus.BAD_REQUEST, "Travel booking not found"));

        currentTravelBooking.setStatus("canceled");

        travelBookingRepository.save(
                currentTravelBooking
        );
    }
}
