/*
 * @creator: Oswaldo Montes
 * @date: December 19, 2023
 *
 */
package com.koombea.web.app.ontoppractice.models.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SyncIntegrationEnum {

    SYNCING("SYNCING"),
    DONE("DONE");

    private final String value;

    SyncIntegrationEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String toString() {
        return this.value;
    }
}
