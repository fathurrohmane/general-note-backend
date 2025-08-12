package com.elkusnandi.playground.mapper;

import com.elkusnandi.playground.entity.Notes;
import com.elkusnandi.playground.response.NotesResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(source = "owner.id", target = "ownerId")
    NotesResponse notesToResponse(Notes notes);
}

