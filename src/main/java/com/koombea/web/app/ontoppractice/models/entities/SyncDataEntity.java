/*
 * @creator: Oswaldo Montes
 * @date: December 19, 2023
 *
 */
package com.koombea.web.app.ontoppractice.models.entities;

import com.koombea.web.app.ontoppractice.models.enums.SyncIntegrationEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sync_integrations")
@Data
public class SyncDataEntity extends BaseEntity {

    @Column(nullable = false)
    private String modelId;

    @Column(nullable = false)
    private SyncIntegrationEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserIntegrationEntity integration;
}
