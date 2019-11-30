package ru.photorex.hw14.service;

import org.springframework.batch.core.ExitStatus;

import java.util.List;

public interface BatchService {

    ExitStatus startJobByName(String jobName);

    List<String> getJobNames();
}
