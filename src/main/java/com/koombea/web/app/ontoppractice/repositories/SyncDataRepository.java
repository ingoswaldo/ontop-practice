/*
 * @creator: Oswaldo Montes
 * @date: December 15, 2023
 *
 */
package com.koombea.web.app.ontoppractice.repositories;

import com.koombea.web.app.ontoppractice.models.entities.SyncDataEntity;
import com.koombea.web.app.ontoppractice.models.enums.SyncIntegrationEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SyncDataRepository extends CrudRepository<SyncDataEntity, String> {

    boolean existsByStatus(SyncIntegrationEnum status);

    boolean existsByIntegrationIdAndModelIdAndStatus(String integrationId, String modelId, SyncIntegrationEnum status);

    Optional<SyncDataEntity> findFirstByIntegrationIdAndModelIdAndStatusOrderByCreatedAtDesc(String integrationId, String modelId, SyncIntegrationEnum status);
}
