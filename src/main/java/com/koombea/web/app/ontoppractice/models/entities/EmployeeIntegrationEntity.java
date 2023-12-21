/*
 * @creator: Oswaldo Montes
 * @date: December 19, 2023
 *
 */
package com.koombea.web.app.ontoppractice.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employee_integrations")
@Data
public class EmployeeIntegrationEntity extends BaseEntity {

    @Column
    private String apiId;

    @Column
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String employmentStatus;

    @Column
    private boolean wasDeleted;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserIntegrationEntity integration;
}
