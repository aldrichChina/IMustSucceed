/******************************************************************
 *    Package:     com.zhy.http.okhttp.callback
 *
 *    Filename:    GenericsCallback.java
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年7月20日 上午11:30:25
 *
 *    Revision:
 *
 *    2016年7月20日 上午11:30:25
 *        - second revision
 *
 *****************************************************************/
package com.zhy.http.okhttp.callback;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Response;

/**
 * @ClassName GenericsCallback
 * @Description TODO(这里用一句话描述这个类的作用)
 * @Date 2016年7月20日 上午11:30:25
 * @version 1.0.0
 */
public abstract class GenericsCallback<T> extends Callback<T> {

    IGenericsSerializator mGenericsSerializator;

    public  GenericsCallback(IGenericsSerializator serializator) {
        mGenericsSerializator = serializator;
    }

    /*
     * (非 Javadoc) Description:
     * @see com.zhy.http.okhttp.callback.Callback#parseNetworkResponse(okhttp3.Response, int)
     */
    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        if (entityClass == string.getClass()) {
            return (T) string;
        }
        T bean = mGenericsSerializator.transform(string, entityClass);
        return bean;
    }
}
