/*
 * @creator: Oswaldo Montes
 * @date: December 14, 2023
 *
 */
package com.koombea.web.app.ontoppractice.models.entities;

import com.koombea.web.app.ontoppractice.dtos.merge.LinkedAccountPayloadDTO;
import com.koombea.web.app.ontoppractice.models.converters.LinkedAccountPayloadDTOConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnTransformer;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_integrations")
@Data
public class UserIntegrationEntity extends BaseEntity {

    @Column(nullable = false)
    private String serviceName;

    @Column
    private String categoryName;

    @Column(nullable = false)
    private String accountName;

    @Convert(converter = LinkedAccountPayloadDTOConverter.class)
    @Column(columnDefinition = "json", nullable = false)
    @ColumnTransformer(write = "?::json")
    private LinkedAccountPayloadDTO payload;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
