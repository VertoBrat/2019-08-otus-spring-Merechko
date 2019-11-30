package ru.photorex.hw14.batch.writers;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.photorex.hw14.model.sql.UserTo;
import ru.photorex.hw14.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserWriter implements ItemWriter<UserTo> {

    private final UserRepository repository;

    @Override
    public void write(List<? extends UserTo> list) throws Exception {
        if (!list.isEmpty()) {
            repository.saveAll(list);
        }
    }
}
