package com.elkusnandi.generalnote.mapper;

import com.elkusnandi.generalnote.entity.ShuttleOutletLocation;
import com.elkusnandi.generalnote.response.ShuttleOutletLocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShuttleOutletLocationMapper {

    ShuttleOutletLocationMapper INSTANCE = Mappers.getMapper(ShuttleOutletLocationMapper.class);

    ShuttleOutletLocationResponse entityToResponse(ShuttleOutletLocation shuttleOutletLocation);

}
