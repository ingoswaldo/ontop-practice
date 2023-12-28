/*
 * @creator: Oswaldo Montes
 * @date: December 14, 2023
 *
 */
package com.koombea.web.app.ontoppractice.controllers.webhooks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.koombea.web.app.ontoppractice.models.entities.UserEntity;
import com.koombea.web.app.ontoppractice.repositories.UserRepository;
import com.koombea.web.app.ontoppractice.services.merge.MergeService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerWebInputException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@AllArgsConstructor
public class MergeWebHooksController extends BaseWebhooksController {

    private final MergeService mergeService;

    private final UserRepository userRepository;

    private final JobLauncher jobLauncher;

    @Qualifier("modelSyncedJob")
    private final Job modelSyncedJob;

    @PostMapping("/merge/linked-accounts")
    public ResponseEntity<Void> linkedAccount(@RequestHeader("X-Merge-Webhook-Signature") String headerSignature, @RequestBody String payload) {
        if (!mergeService.isAllowed(payload, headerSignature)) {
            throw new ServerWebInputException("header signature is not valid");
        }

        try {
            UserEntity user = userRepository.findById("310a3a9c-9b5d-11ee-b9d1-0242ac120002").get();
            mergeService.createUserIntegrationFromPayload(payload, user);
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException exception) {
            throw new HttpServerErrorException(exception.getMessage(), INTERNAL_SERVER_ERROR, String.valueOf(INTERNAL_SERVER_ERROR.value()), null, null, null);
        }
    }

    @PostMapping("/merge/model-synced")
    public ResponseEntity<Void> modelSynced(@RequestHeader("X-Merge-Webhook-Signature") String headerSignature, @RequestBody String payload) {
        if (!mergeService.isAllowed(payload, headerSignature)) {
            throw new ServerWebInputException("header signature is not valid");
        }

        try {
            JobParameters parameters = new JobParametersBuilder()
                    .addString("payload", payload)
                    .toJobParameters();

            jobLauncher.run(modelSyncedJob, parameters);

            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            throw new HttpServerErrorException(exception.getMessage(), INTERNAL_SERVER_ERROR, String.valueOf(INTERNAL_SERVER_ERROR.value()), null, null, null);
        }
    }
}
