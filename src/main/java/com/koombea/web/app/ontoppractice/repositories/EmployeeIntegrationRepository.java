/*
 * @creator: Oswaldo Montes
 * @date: December 15, 2023
 *
 */
package com.koombea.web.app.ontoppractice.repositories;

import com.koombea.web.app.ontoppractice.models.entities.EmployeeIntegrationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeIntegrationRepository extends CrudRepository<EmployeeIntegrationEntity, String> {

    Optional<EmployeeIntegrationEntity> findFirstByIntegrationIdAndApiId(String integrationId, String apiId);
}
