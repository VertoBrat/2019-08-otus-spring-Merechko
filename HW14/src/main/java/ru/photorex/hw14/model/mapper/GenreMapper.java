package ru.photorex.hw14.model.mapper;

import org.mapstruct.Mapper;
import ru.photorex.hw14.model.sql.GenreTo;

@Mapper(componentModel = "spring")
public interface GenreMapper extends BaseMapper<String, GenreTo> {

   default GenreTo toTo(String genre) {
       return new GenreTo(genre);
   }
}
