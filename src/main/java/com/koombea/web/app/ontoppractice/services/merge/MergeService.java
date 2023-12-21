/*
 * @creator: Oswaldo Montes
 * @date: December 14, 2023
 *
 */
package com.koombea.web.app.ontoppractice.services.merge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koombea.web.app.ontoppractice.dtos.merge.LinkedAccountPayloadDTO;
import com.koombea.web.app.ontoppractice.models.entities.UserEntity;
import com.koombea.web.app.ontoppractice.models.entities.UserIntegrationEntity;
import com.koombea.web.app.ontoppractice.repositories.UserIntegrationRepository;
import com.koombea.web.app.ontoppractice.repositories.UserRepository;
import com.koombea.web.app.ontoppractice.shared.utils.HmacSha256Utils;
import com.koombea.web.app.ontoppractice.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MergeService {

    private final UserRepository userRepository;

    private final UserIntegrationRepository integrationRepository;

    @Autowired
    public MergeService(UserRepository userRepository, UserIntegrationRepository integrationRepository) {
        this.userRepository = userRepository;
        this.integrationRepository = integrationRepository;
    }

    public boolean isAllowed(String payload, String headerSignature) {
        String headerSignatureCleaned = StringUtils.removeLastCharOf(headerSignature);
        return headerSignatureCleaned.equals(HmacSha256Utils.encodeBase64URLSafeString(payload));
    }

    public UserIntegrationEntity createUserIntegrationFromPayload(String payload, UserEntity user) throws JsonProcessingException {
        LinkedAccountPayloadDTO linkedAccount = new ObjectMapper().readValue(payload, LinkedAccountPayloadDTO.class);
        UserIntegrationEntity integration = new UserIntegrationEntity();
        integration.setUser(user);
        integration.setServiceName("merge");
        integration.setAccountName(linkedAccount.getLinkedAccount().getIntegration());
        integration.setCategoryName(linkedAccount.getLinkedAccount().getCategory());
        integration.setPayload(linkedAccount);
        return integrationRepository.save(integration);
    }
}
