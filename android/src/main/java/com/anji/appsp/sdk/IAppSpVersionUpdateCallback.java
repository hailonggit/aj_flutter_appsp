package com.anji.appsp.sdk;

import com.anji.appsp.sdk.model.SpRespUpdateModel;

/**
 * Version info callback
 *
 * @author chenhailong
 * Create Date:2020.7.2
 */
public interface IAppSpVersionUpdateCallback {
    /**
     * version update
     * We may update app when higher version releases,there are two ways to
     * update app,1,download apk in page 2,download apk in H5 page,
     * @param updateModel update result
     */
    void update(SpRespUpdateModel updateModel);
}
