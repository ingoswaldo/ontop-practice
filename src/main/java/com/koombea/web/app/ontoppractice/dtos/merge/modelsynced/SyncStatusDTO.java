/*
 * @creator: Oswaldo Montes
 * @date: December 22, 2023
 *
 */
package com.koombea.web.app.ontoppractice.dtos.merge.modelsynced;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncStatusDTO {

    @JsonProperty("model_id")
    private String modelId;

    private String status;
}
