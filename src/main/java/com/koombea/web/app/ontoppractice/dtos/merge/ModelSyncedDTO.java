/*
 * @creator: Oswaldo Montes
 * @date: December 15, 2023
 *
 */
package com.koombea.web.app.ontoppractice.dtos.merge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.koombea.web.app.ontoppractice.dtos.merge.modelsynced.DataDTO;
import com.koombea.web.app.ontoppractice.dtos.merge.modelsynced.LinkedAccountDTO;
import com.koombea.web.app.ontoppractice.dtos.merge.shared.HookDTO;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelSyncedDTO {

    private HookDTO hook;

    @JsonProperty("linked_account")
    private LinkedAccountDTO linkedAccount;

    private DataDTO data;
}
