package com.example.JpaVueShop_backend.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchJobLauncher {

    private final JobLauncher jobLauncher;
    private final BatchJob batchJob;

    @Scheduled(cron = "0 0/5 * * * *")
    public void runJob() {
        Map<String, JobParameter> confMap = new HashMap<>();
        confMap.put("time", new JobParameter(String.valueOf(LocalDateTime.now())));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(batchJob.elasticsearchJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException| JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}
