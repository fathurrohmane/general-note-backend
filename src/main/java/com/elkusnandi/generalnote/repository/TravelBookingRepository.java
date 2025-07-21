package com.elkusnandi.generalnote.repository;

import com.elkusnandi.generalnote.entity.TravelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TravelBookingRepository extends JpaRepository<TravelBooking, UUID> {

    List<TravelBooking> findByCustomerId(Long customerId);

    List<TravelBooking> findByScheduleId(UUID scheduleId);

    Optional<TravelBooking> findByScheduleIdAndSeatNumberAndStatusIn(UUID scheduleId, String seatNumber, List<String> status);

}
