/*
 * @creator: Oswaldo Montes
 * @date: December 22, 2023
 *
 */
package com.koombea.web.app.ontoppractice.config.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MergeModelSyncedJobConfig {

    @Bean
    public Job modelSyncedJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("modelSyncedJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean("simpleAsyncTaskExecutor")
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("simple_async_task");
    }

    @Bean
    public Step step1(@Qualifier("simpleAsyncTaskExecutor") TaskExecutor taskExecutor, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet(importMergeDataSyncedTasklet(null), transactionManager)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    @StepScope
    public ImportMergeDataSyncedTasklet importMergeDataSyncedTasklet(@Value("#{jobParameters['payload']}") String payload) {
        ImportMergeDataSyncedTasklet tasklet = new ImportMergeDataSyncedTasklet();
        tasklet.setPayload(payload);

        return tasklet;
    }
}
