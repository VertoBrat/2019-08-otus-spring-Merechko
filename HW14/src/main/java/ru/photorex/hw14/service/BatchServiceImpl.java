package ru.photorex.hw14.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {

    private static final Logger logger = LoggerFactory.getLogger(BatchServiceImpl.class);
    private final JobLauncher launcher;
    private final List<Job> jobs;

    @Override
    public List<String> getJobNames() {
        return this.jobs.stream().map(Job::getName).collect(Collectors.toList());
    }

    @Override
    public ExitStatus startJobByName(String jobName) {
        Optional<Job> job = jobs.stream().filter(j -> j.getName().equals(jobName)).findFirst();
        if (job.isPresent()) {
            try {
                JobExecution jobExecution = launcher.run(job.get(), new JobParameters());
                return jobExecution.getExitStatus();
            } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
                logger.error(e.getMessage());
                return ExitStatus.FAILED;
            }
        }
        return new ExitStatus("NO_JOB_WITH_THIS_NAME");
    }
}
