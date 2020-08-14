package com.anji.appsp.sdk;

import com.anji.appsp.sdk.model.SpRespNoticeModel;

/**
 * notice info callback
 *
 * @author chenhailong
 * Create Date:2020.7.2
 */
public interface IAppSpNoticeCallback {
    /**
     * notice service
     * We will display notice as dialog type or scroll type when we have important updates or changes occur
     *
     * @param noticeModel we may have several notices,we give way to developer to handle with display case,
     *                    for example,show as dialog or as other types
     */
    void notice(SpRespNoticeModel noticeModel);
}
