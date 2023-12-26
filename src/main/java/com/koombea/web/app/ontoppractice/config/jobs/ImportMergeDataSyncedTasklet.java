/*
 * @creator: Oswaldo Montes
 * @date: December 22, 2023
 *
 */
package com.koombea.web.app.ontoppractice.config.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koombea.web.app.ontoppractice.dtos.merge.ModelSyncedDTO;
import com.koombea.web.app.ontoppractice.models.entities.UserIntegrationEntity;
import com.koombea.web.app.ontoppractice.services.EmployeeIntegrationService;
import com.koombea.web.app.ontoppractice.services.UserIntegrationService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

@Component
public class ImportMergeDataSyncedTasklet implements Tasklet, InitializingBean {
    @Setter
    private String payload;

    @Getter
    @Value("${hris.employee.model-id}")
    private String modelId;

    @Autowired
    private UserIntegrationService userIntegrationService;

    @Autowired
    private EmployeeIntegrationService employeeIntegrationService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws JsonProcessingException {
        ModelSyncedDTO modelSynced = new ObjectMapper().readValue(payload, ModelSyncedDTO.class);
        Optional<UserIntegrationEntity> userIntegration = userIntegrationService.findByAccountToken(modelSynced.getLinkedAccount().getAccountToken());
        if (Objects.equals(modelSynced.getData().getSyncStatus().getModelId(), getModelId())) {
            userIntegration.ifPresent(integration -> employeeIntegrationService.importEmployeesFromMerge(integration));
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() {}
}
