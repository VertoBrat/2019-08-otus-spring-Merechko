package ru.photorex.hw9.to.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.photorex.hw9.to.GenreTo;

@Mapper(componentModel = "spring")
public interface GenreMapper extends BaseMapper<String, GenreTo> {

    @Mapping(target = "genre", source = "genre")
    GenreTo toTo(String genre);


    default String toEntity(GenreTo to) {
        return to.getGenre();
    }
}
