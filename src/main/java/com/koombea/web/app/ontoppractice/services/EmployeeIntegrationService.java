/*
 * @creator: Oswaldo Montes
 * @date: December 19, 2023
 *
 */
package com.koombea.web.app.ontoppractice.services;

import com.koombea.web.app.ontoppractice.models.entities.EmployeeIntegrationEntity;
import com.koombea.web.app.ontoppractice.models.entities.SyncDataEntity;
import com.koombea.web.app.ontoppractice.models.entities.UserIntegrationEntity;
import com.koombea.web.app.ontoppractice.models.enums.SyncIntegrationEnum;
import com.koombea.web.app.ontoppractice.repositories.EmployeeIntegrationRepository;
import com.koombea.web.app.ontoppractice.services.merge.hris.EmployeeHrisService;
import com.merge.api.resources.hris.types.Employee;
import com.merge.api.resources.hris.types.PaginatedEmployeeList;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Getter
@Setter
public class EmployeeIntegrationService {

    @Value("${hris.employee.model-id}")
    private String modelId;

    private SyncDataEntity syncDataEntity;

    private final EmployeeIntegrationRepository employeeIntegrationRepository;

    private final SyncDataService syncDataService;

    @Autowired
    public EmployeeIntegrationService(EmployeeIntegrationRepository employeeIntegrationRepository, SyncDataService syncDataService) {
        this.employeeIntegrationRepository = employeeIntegrationRepository;
        this.syncDataService = syncDataService;
    }

    public void importEmployeesFromMerge(UserIntegrationEntity userIntegration) {
        Optional<SyncDataEntity> syncData = syncDataService.findLatestSyncDoneByIntegrationIdAndModel(userIntegration.getId(), getModelId());
        Optional<String> cursor = Optional.empty();
        AtomicReference<Optional<OffsetDateTime>> modifiedAfter = new AtomicReference<>(Optional.empty());
        syncData.ifPresent(sync -> modifiedAfter.set(Optional.of(OffsetDateTime.of(sync.getCreatedAt(), ZoneOffset.of("-5")))));

        boolean importing = true;
        while (importing) {
            EmployeeHrisService employeeHrisService = new EmployeeHrisService(userIntegration.getPayload().getData().getAccountToken());
            PaginatedEmployeeList paginatedEmployeeList = employeeHrisService.getAllPaginated(cursor, Optional.empty(), modifiedAfter.get());
            processPaginatedEmployeeList(paginatedEmployeeList, userIntegration);
            cursor = paginatedEmployeeList.getNext();

            if (cursor.isEmpty()) {
                importing = false;
                syncDataService.markLastIntegrationModelSyncingAsDone(userIntegration.getId(), getModelId());
            }
        }
    }

    public EmployeeIntegrationEntity createOrUpdateFromEmployeeHrisMerge(@NotNull Employee employeeMerge, @NotNull UserIntegrationEntity userIntegration) {
        EmployeeIntegrationEntity employee = findByIntegrationAndApiIdOrNew(userIntegration.getId(), employeeMerge.getId().orElse(""));
        employee.setApiId(employeeMerge.getId().get());
        employee.setIntegration(userIntegration);
        employee.setFirstName(employeeMerge.getFirstName().orElse(""));
        employee.setLastName(employeeMerge.getLastName().orElse(""));
        employee.setEmail(employeeMerge.getPersonalEmail().orElse(employeeMerge.getWorkEmail().orElse("")));
        return employeeIntegrationRepository.save(employee);
    }

    public EmployeeIntegrationEntity findByIntegrationAndApiIdOrNew(String integrationId, @NotNull String apiId) {
        if (apiId.isBlank() || integrationId.isBlank()) return new EmployeeIntegrationEntity();

        return employeeIntegrationRepository.findFirstByIntegrationIdAndApiId(integrationId, apiId).orElse(new EmployeeIntegrationEntity());
    }

    private void processPaginatedEmployeeList(@NotNull PaginatedEmployeeList paginatedEmployeeList, UserIntegrationEntity userIntegration) {
        Optional<List<Employee>> employeeList = paginatedEmployeeList.getResults();
        employeeList.ifPresent(employeeResults -> processEmployeeList(employeeResults, userIntegration));
    }

    private void processEmployeeList(@NotNull List<Employee> employeeList, UserIntegrationEntity userIntegration) {
        if (employeeList.isEmpty()) return;

        syncDataService.create(userIntegration, getModelId(), SyncIntegrationEnum.SYNCING);
        employeeList.forEach(employeeMerge -> createOrUpdateFromEmployeeHrisMerge(employeeMerge, userIntegration));
    }
}
