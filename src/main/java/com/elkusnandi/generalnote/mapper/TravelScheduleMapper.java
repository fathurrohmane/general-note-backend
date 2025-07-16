package com.elkusnandi.generalnote.mapper;

import com.elkusnandi.generalnote.entity.TravelSchedule;
import com.elkusnandi.generalnote.response.TravelScheduleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TravelScheduleMapper {

    TravelScheduleMapper INSTANCE = Mappers.getMapper(TravelScheduleMapper.class);

    TravelScheduleResponse entityToResponse(TravelSchedule travelSchedule);

}
