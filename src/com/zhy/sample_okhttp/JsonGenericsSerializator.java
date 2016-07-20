/******************************************************************
 *    Package:     com.zhy.sample_okhttp
 *
 *    Filename:    JsonGenericsSerializator.java
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年7月20日 下午1:50:19
 *
 *    Revision:
 *
 *    2016年7月20日 下午1:50:19
 *        - second revision
 *
 *****************************************************************/
package com.zhy.sample_okhttp;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.IGenericsSerializator;


/**
 * @ClassName JsonGenericsSerializator
 * @Description TODO(这里用一句话描述这个类的作用)
 * @Date 2016年7月20日 下午1:50:19
 * @version 1.0.0
 */
public class JsonGenericsSerializator implements IGenericsSerializator{
    Gson mGson=new Gson();

    /* (非 Javadoc)
     * Description:
     * @see com.zhy.http.okhttp.callback.IGenericsSerializator#transform(java.lang.String, java.lang.Class)
     */
    @Override
    public <T> T transform(String response, Class<T> classOfT) {
        return mGson.fromJson(response, classOfT);
    }

}
