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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MergeWebHooksController extends BaseWebhooksController {

    private final MergeService mergeService;

    private final UserRepository userRepository;

    @Autowired
    public MergeWebHooksController(MergeService mergeService, UserRepository userRepository) {
        this.mergeService = mergeService;
        this.userRepository = userRepository;
    }

    @PostMapping("/merge/linked-accounts")
    public ResponseEntity<Void> linkedAccount(@RequestHeader("X-Merge-Webhook-Signature") String headerSignature, @RequestBody String payload) {
        if (!mergeService.isAllowed(payload, headerSignature)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            UserEntity user = userRepository.findById("310a3a9c-9b5d-11ee-b9d1-0242ac120002").get();
            mergeService.createUserIntegrationFromPayload(payload, user);
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
