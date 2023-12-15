/*
 * @creator: Oswaldo Montes
 * @date: December 14, 2023
 *
 */
package com.koombea.web.app.ontoppractice.services.merge;

import com.merge.api.MergeApiClient;
import lombok.Getter;

@Getter
public class BaseMergeApiService {

    private final MergeApiClient mergeApiClient;

    public BaseMergeApiService(String accountToken) {
        this.mergeApiClient = MergeApiClient.builder()
                .accountToken(accountToken)
                .apiKey("TKGRpVtyBwl3wcro42s7O3VLOhLRF_q-Sj2oSpoZxf7we9v1mWta5g")
                .build();
    }
}
