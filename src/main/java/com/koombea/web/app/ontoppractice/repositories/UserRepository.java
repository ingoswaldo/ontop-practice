/*
 * @creator: Oswaldo Montes
 * @date: December 15, 2023
 *
 */
package com.koombea.web.app.ontoppractice.repositories;

import com.koombea.web.app.ontoppractice.models.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {}
