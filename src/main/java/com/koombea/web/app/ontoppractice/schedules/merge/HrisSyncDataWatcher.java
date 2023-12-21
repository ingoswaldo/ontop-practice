/*
 * @creator: Oswaldo Montes
 * @date: December 18, 2023
 *
 */
package com.koombea.web.app.ontoppractice.schedules.merge;

import com.koombea.web.app.ontoppractice.models.entities.UserIntegrationEntity;
import com.koombea.web.app.ontoppractice.services.EmployeeIntegrationService;
import com.koombea.web.app.ontoppractice.services.SyncDataService;
import com.koombea.web.app.ontoppractice.services.UserIntegrationService;
import com.koombea.web.app.ontoppractice.services.merge.hris.SyncHrisService;
import com.merge.api.resources.hris.types.PaginatedSyncStatusList;
import com.merge.api.resources.hris.types.SyncStatus;
import com.merge.api.resources.hris.types.SyncStatusStatusEnum;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HrisSyncDataWatcher extends BaseMergeSyncDataWatcher {

    @Value("${hris.employee.model-id}")
    private String modelId;

    private final UserIntegrationService userIntegrationService;

    private final SyncDataService syncDataService;

    private final EmployeeIntegrationService employeeIntegrationService;

    @Autowired
    public HrisSyncDataWatcher(UserIntegrationService userIntegrationService, SyncDataService syncDataService, EmployeeIntegrationService employeeIntegrationService) {
        this.userIntegrationService = userIntegrationService;
        this.syncDataService = syncDataService;
        this.employeeIntegrationService = employeeIntegrationService;
    }

    @Override
    @Scheduled(fixedDelay = 5000)
    public void checkSyncData() {
        if (syncDataService.existsSyncing()) return;

        List<UserIntegrationEntity> services = userIntegrationService.findAllByCategoryNameAndServiceName("hris", "merge");
        services.forEach(this::getSyncStatus);
    }

    private void getSyncStatus(@NotNull UserIntegrationEntity userIntegration) {
        SyncHrisService syncHrisService = new SyncHrisService(userIntegration.getPayload().getData().getAccountToken());
        Optional<String> cursor = Optional.empty();
        boolean syncing = true;

        while (syncing) {
            PaginatedSyncStatusList paginatedSyncStatusList = syncHrisService.getAllPaginated(cursor);
            processPaginatedSyncStatusList(paginatedSyncStatusList, userIntegration);
            cursor = paginatedSyncStatusList.getNext();

            if (cursor.isEmpty()) syncing = false;
        }
    }

    private void processPaginatedSyncStatusList(@NotNull PaginatedSyncStatusList paginatedSyncStatusList, UserIntegrationEntity userIntegration) {
        Optional<List<SyncStatus>> syncStatusList = paginatedSyncStatusList.getResults();
        syncStatusList.ifPresent(syncStatuses -> processSyncDataList(syncStatuses, userIntegration));
    }

    private void processSyncDataList(List<SyncStatus> syncStatuses, UserIntegrationEntity userIntegration) {
        filterByEmployee(syncStatuses).forEach(syncStatus -> processSyncDataEmployee(syncStatus, userIntegration));
    }

    private void processSyncDataEmployee(@NotNull SyncStatus syncStatus, UserIntegrationEntity userIntegration) {
        if (syncStatus.getStatus().equals(SyncStatusStatusEnum.DONE)) {
            employeeIntegrationService.importEmployeesFromMerge(userIntegration);
        }
    }

    private List<SyncStatus> filterByEmployee(@NotNull List<SyncStatus> syncStatuses) {
        return syncStatuses.stream().filter(syncStatus -> syncStatus.getModelId().equals(modelId)).toList();
    }
}
