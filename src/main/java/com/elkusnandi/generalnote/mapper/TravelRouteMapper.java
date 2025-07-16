package com.elkusnandi.generalnote.mapper;

import com.elkusnandi.generalnote.entity.TravelRoute;
import com.elkusnandi.generalnote.response.TravelRouteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TravelRouteMapper {

    TravelRouteMapper INSTANCE = Mappers.getMapper(TravelRouteMapper.class);

    TravelRouteResponse entityToResponse(TravelRoute travelRoute);
}
