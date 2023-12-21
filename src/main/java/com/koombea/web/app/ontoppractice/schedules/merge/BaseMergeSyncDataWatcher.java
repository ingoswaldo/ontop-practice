/*
 * @creator: Oswaldo Montes
 * @date: December 18, 2023
 *
 */
package com.koombea.web.app.ontoppractice.schedules.merge;

import org.springframework.scheduling.annotation.Scheduled;

public abstract class BaseMergeSyncDataWatcher {

    @Scheduled
    public  abstract void checkSyncData();
}
