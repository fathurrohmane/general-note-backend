package com.elkusnandi.generalnote.repository;

import com.elkusnandi.generalnote.entity.Travel;
import com.elkusnandi.generalnote.entity.TravelSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TravelScheduleRepository extends JpaRepository<TravelSchedule, UUID> {

    List<TravelSchedule> findByDateAndTravel(LocalDate date, Travel travel);

    List<TravelSchedule> findByTravel(Travel travel);

    Optional<TravelSchedule> findByIdAndTravelId(UUID id, UUID travelId);

    void deleteByIdAndTravelId(UUID id, UUID travelId);

}
