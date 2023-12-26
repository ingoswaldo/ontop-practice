/*
 * @creator: Oswaldo Montes
 * @date: December 22, 2023
 *
 */
package com.koombea.web.app.ontoppractice.dtos.merge.modelsynced;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataDTO {

    @JsonProperty("integration_name")
    private String integration;

    @JsonProperty("synced_fields")
    private List<String> syncedFields;

    @JsonProperty("sync_status")
    private SyncStatusDTO syncStatus;
}
