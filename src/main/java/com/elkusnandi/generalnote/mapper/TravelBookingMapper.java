package com.elkusnandi.generalnote.mapper;

import com.elkusnandi.generalnote.entity.TravelBooking;
import com.elkusnandi.generalnote.response.TravelBookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TravelBookingMapper {

    TravelBookingMapper INSTANCE = Mappers.getMapper(TravelBookingMapper.class);

    TravelBookingResponse entityToResponse(TravelBooking travelBooking);

}
