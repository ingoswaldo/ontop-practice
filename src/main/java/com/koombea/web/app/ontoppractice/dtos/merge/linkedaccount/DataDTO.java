/*
 * @creator: Oswaldo Montes
 * @date: December 15, 2023
 *
 */
package com.koombea.web.app.ontoppractice.dtos.merge.linkedaccount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataDTO {

    @JsonProperty("account_token")
    private String accountToken;

    @JsonProperty("is_relink")
    private Boolean isRelink;
}
