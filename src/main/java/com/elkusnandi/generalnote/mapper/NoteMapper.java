package com.elkusnandi.generalnote.mapper;

import com.elkusnandi.generalnote.entity.Notes;
import com.elkusnandi.generalnote.response.NotesResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(source = "owner.id", target = "ownerId")
    NotesResponse notesToResponse(Notes notes);
}
