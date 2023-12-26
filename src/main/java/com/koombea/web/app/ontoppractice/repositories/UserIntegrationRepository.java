/*
 * @creator: Oswaldo Montes
 * @date: December 15, 2023
 *
 */
package com.koombea.web.app.ontoppractice.repositories;

import com.koombea.web.app.ontoppractice.models.entities.UserIntegrationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserIntegrationRepository extends CrudRepository<UserIntegrationEntity, String> {

    List<UserIntegrationEntity> findAllByCategoryNameAndServiceNameEquals(String categoryName, String serviceName);

    @Query(value = "SELECT * FROM user_integrations WHERE payload ->'data' ->>'account_token' = :accountToken", nativeQuery = true)
    Optional<UserIntegrationEntity> findFirstByPayloadDataAccountToken(@Param("accountToken") String accountToken);
}
