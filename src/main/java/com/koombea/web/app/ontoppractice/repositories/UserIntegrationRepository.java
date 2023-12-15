/*
 * @creator: Oswaldo Montes
 * @date: December 15, 2023
 *
 */
package com.koombea.web.app.ontoppractice.repositories;

import com.koombea.web.app.ontoppractice.models.entities.UserIntegrationEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserIntegrationRepository extends CrudRepository<UserIntegrationEntity, String> {
}
