package com.elkusnandi.generalnote.service.impl;

import com.elkusnandi.generalnote.entity.Travel;
import com.elkusnandi.generalnote.entity.TravelRoute;
import com.elkusnandi.generalnote.entity.TravelSchedule;
import com.elkusnandi.generalnote.exception.UserFaultException;
import com.elkusnandi.generalnote.mapper.TravelScheduleMapper;
import com.elkusnandi.generalnote.repository.TravelRepository;
import com.elkusnandi.generalnote.repository.TravelRouteRepository;
import com.elkusnandi.generalnote.repository.TravelScheduleRepository;
import com.elkusnandi.generalnote.request.TravelScheduleRequest;
import com.elkusnandi.generalnote.response.TravelScheduleResponse;
import com.elkusnandi.generalnote.service.TravelScheduleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TravelScheduleServiceImpl implements TravelScheduleService {

    private final TravelScheduleRepository scheduleRepository;

    private final TravelRepository travelRepository;

    private final TravelRouteRepository travelRouteRepository;

    @Autowired
    public TravelScheduleServiceImpl(
            TravelScheduleRepository scheduleRepository,
            TravelRepository travelRepository,
            TravelRouteRepository travelRouteRepository
    ) {
        this.scheduleRepository = scheduleRepository;
        this.travelRepository = travelRepository;
        this.travelRouteRepository = travelRouteRepository;
    }

    @Override
    public List<TravelScheduleResponse> getTravelSchedule(UUID travelId, LocalDate date) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new UserFaultException(
                HttpStatus.BAD_REQUEST,
                "Travel not found"
        ));
        if (date == null) {
            return scheduleRepository.findByTravel(travel).stream().map(TravelScheduleMapper.INSTANCE::entityToResponse)
                    .toList();
        } else {
            return scheduleRepository.findByDateAndTravel(date, travel).stream()
                    .map(TravelScheduleMapper.INSTANCE::entityToResponse).toList();
        }
    }

    @Override
    public TravelScheduleResponse createSchedule(UUID travelRouteId, TravelScheduleRequest schedule) {
        TravelRoute travelRoute = travelRouteRepository.findById(travelRouteId).orElseThrow(() -> new UserFaultException(
                HttpStatus.BAD_REQUEST,
                "Travel not found"
        ));

        TravelSchedule travelSchedule = scheduleRepository.save(new TravelSchedule(
                UUID.randomUUID(),
                schedule.getDate(),
                schedule.getTime(),
                travelRoute
        ));
        return TravelScheduleMapper.INSTANCE.entityToResponse(travelSchedule);
    }

    @Override
    public TravelScheduleResponse editSchedule(UUID travelId, UUID travelScheduleId, TravelScheduleRequest schedule) {
        TravelSchedule currentTravelSchedule =
                scheduleRepository.findByIdAndTravelId(travelScheduleId, travelId)
                        .orElseThrow(() -> new UserFaultException(
                                HttpStatus.BAD_REQUEST, "Travel schedule not found"));

        currentTravelSchedule.setDate(schedule.getDate());
        currentTravelSchedule.setTime(schedule.getTime());

        TravelSchedule travelSchedule = scheduleRepository.save(currentTravelSchedule);
        return TravelScheduleMapper.INSTANCE.entityToResponse(travelSchedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(UUID travelId, UUID travelScheduleId) {
        scheduleRepository.findByIdAndTravelId(travelScheduleId, travelId)
                .orElseThrow(() -> new UserFaultException(
                        HttpStatus.BAD_REQUEST, "Travel schedule not found"));
        scheduleRepository.deleteByIdAndTravelId(travelScheduleId, travelId);
    }
}
