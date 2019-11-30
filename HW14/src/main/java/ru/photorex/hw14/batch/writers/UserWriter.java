package ru.photorex.hw14.batch.writers;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.photorex.hw14.model.sql.UserTo;
import ru.photorex.hw14.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserWriter implements ItemWriter<UserTo> {

    private static final String USERS = "users";
    private final UserRepository repository;
    private StepExecution stepExecution;

    @Override
    public void write(List<? extends UserTo> list) throws Exception {
        if (!list.isEmpty()) {
            List<UserTo> dbList = new ArrayList<>(repository.saveAll(list));
            ExecutionContext stepContext = this.stepExecution.getExecutionContext();
            Map<String, Long> userMap = new HashMap<>();
            if (stepContext.get(USERS) != null) {
                userMap = (Map<String, Long>) stepContext.get(USERS);
            }
            fillUserMap(dbList, userMap);
            stepContext.put(USERS, userMap);
        }
    }

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    private void fillUserMap(List<UserTo> list, Map<String, Long> map) {
        for (UserTo u : list) {
            map.put(u.getUserName(), u.getId());
        }
    }
}
