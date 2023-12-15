/*
 * @creator: Oswaldo Montes
 * @date: December 14, 2023
 *
 */
package com.koombea.web.app.ontoppractice.controllers.webhooks;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @Autowired
    public MergeWebHooksController(MergeService mergeService) {
        this.mergeService = mergeService;
    }

    @PostMapping("/merge/linked-accounts")
    public ResponseEntity<Void> linkedAccount(@RequestHeader("X-Merge-Webhook-Signature") String headerSignature, @RequestBody String payload) throws JsonProcessingException {
        if (!mergeService.isAllowed(payload, headerSignature)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            mergeService.createFromPayload(payload);
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
