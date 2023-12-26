/*
 * @creator: Oswaldo Montes
 * @date: December 14, 2023
 *
 */
package com.koombea.web.app.ontoppractice.services.merge.hris;

import com.merge.api.resources.hris.syncstatus.SyncStatusClient;
import com.merge.api.resources.hris.syncstatus.requests.SyncStatusListRequest;
import com.merge.api.resources.hris.types.PaginatedSyncStatusList;
import lombok.Getter;

import java.util.Optional;

@Getter
public class SyncHrisService extends BaseHrisService {

    private final SyncStatusClient statusClient;

    public SyncHrisService(String accountToken) {
        super(accountToken);
        this.statusClient = getClient().syncStatus();
    }

    public PaginatedSyncStatusList getAllPaginated(Optional<String> cursor) {
        SyncStatusListRequest request = SyncStatusListRequest.builder()
                .cursor(cursor)
                .build();

        return getStatusClient().list(request);
    }
}
