/*
 * @creator: Oswaldo Montes
 * @date: December 19, 2023
 *
 */
package com.koombea.web.app.ontoppractice.services;

import com.koombea.web.app.ontoppractice.models.entities.SyncDataEntity;
import com.koombea.web.app.ontoppractice.models.entities.UserIntegrationEntity;
import com.koombea.web.app.ontoppractice.models.enums.SyncIntegrationEnum;
import com.koombea.web.app.ontoppractice.repositories.SyncDataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SyncDataService {

    private final SyncDataRepository syncDataRepository;

    public SyncDataService(SyncDataRepository syncDataRepository) {
        this.syncDataRepository = syncDataRepository;
    }

    public SyncDataEntity create(UserIntegrationEntity integration, String modelId, SyncIntegrationEnum status) {
        SyncDataEntity syncData = new SyncDataEntity();
        syncData.setModelId(modelId);
        syncData.setIntegration(integration);
        syncData.setStatus(status);

        return syncDataRepository.save(syncData);
    }

    public void markLastIntegrationModelSyncingAsDone(String integrationId, String modelId) {
        Optional<SyncDataEntity> syncDataSyncing = findLatestSyncingByIntegrationIdAndModel(integrationId, modelId);
        if (syncDataSyncing.isEmpty()) return;

        SyncDataEntity syncData = syncDataSyncing.get();
        syncData.setStatus(SyncIntegrationEnum.DONE);
        syncDataRepository.save(syncData);
    }

    public boolean existsSyncing() {
        return syncDataRepository.existsByStatus(SyncIntegrationEnum.SYNCING);
    }

    public Optional<SyncDataEntity> findLatestSyncDoneByIntegrationIdAndModel(String integrationId, String modelId) {
        return syncDataRepository.findFirstByIntegrationIdAndModelIdAndStatusOrderByCreatedAtDesc(integrationId, modelId, SyncIntegrationEnum.DONE);
    }

    public Optional<SyncDataEntity> findLatestSyncingByIntegrationIdAndModel(String integrationId, String modelId) {
        return syncDataRepository.findFirstByIntegrationIdAndModelIdAndStatusOrderByCreatedAtDesc(integrationId, modelId, SyncIntegrationEnum.SYNCING);
    }
}
