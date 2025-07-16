package com.elkusnandi.generalnote.mapper;

import com.elkusnandi.generalnote.entity.Travel;
import com.elkusnandi.generalnote.response.TravelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TravelMapper {

    TravelMapper INSTANCE = Mappers.getMapper(TravelMapper.class);

    TravelResponse entityToResponse(Travel travel);

}
