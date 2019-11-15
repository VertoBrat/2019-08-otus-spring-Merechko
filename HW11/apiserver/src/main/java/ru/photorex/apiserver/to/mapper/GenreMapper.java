package ru.photorex.apiserver.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.photorex.apiserver.to.GenreTo;

@Mapper(componentModel = "spring")
public interface GenreMapper extends BaseMapper<String, GenreTo> {

    @Mapping(target = "name", source = "genre")
    GenreTo toTo(String genre);


    default String toEntity(GenreTo to) {
        return to.getName();
    }
}
