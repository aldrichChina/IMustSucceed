/******************************************************************
 *    Package:     com.zhy.http.okhttp.callback
 *
 *    Filename:    IGenericsSerializator.java
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年7月20日 上午11:36:15
 *
 *    Revision:
 *
 *    2016年7月20日 上午11:36:15
 *        - second revision
 *
 *****************************************************************/
package com.zhy.http.okhttp.callback;


/**
 * @ClassName IGenericsSerializator
 * @Description TODO(这里用一句话描述这个类的作用)
 * @Date 2016年7月20日 上午11:36:15
 * @version 1.0.0
 */
public interface IGenericsSerializator {
    <T> T transform(String response,Class<T> classOfT);
}
