/*
 * @creator: Oswaldo Montes
 * @date: December 14, 2023
 *
 */
package com.koombea.web.app.ontoppractice.services.merge.hris;

import com.koombea.web.app.ontoppractice.services.merge.BaseMergeApiService;
import com.merge.api.resources.hris.HrisClient;
import lombok.Getter;

@Getter
public class BaseHrisService extends BaseMergeApiService {

    private final HrisClient client;

    public BaseHrisService(String accountToken) {
        super(accountToken);
        this.client = getMergeApiClient().hris();
    }
}
