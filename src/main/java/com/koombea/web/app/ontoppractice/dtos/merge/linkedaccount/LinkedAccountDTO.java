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
public class LinkedAccountDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("integration")
    private String integration;

    @JsonProperty("end_user_origin_id")
    private String endUserOriginId;

    @JsonProperty("end_user_organization_name")
    private String endUserOrganizationName;

    @JsonProperty("end_user_email_address")
    private String endUserEmailAddress;

    @JsonProperty("category")
    private String category;
}
