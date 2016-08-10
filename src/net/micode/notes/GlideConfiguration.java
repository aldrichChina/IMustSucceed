/******************************************************************
 *    Package:     net.micode.notes
 *
 *    Filename:    GlideConfiguration.java
 *
 *    @version:    1.0.0
 *
 *    Create at:   2016年8月9日 下午1:51:59
 *
 *    Revision:
 *
 *    2016年8月9日 下午1:51:59
 *        - second revision
 *
 *****************************************************************/
package net.micode.notes;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;


/**
 * @ClassName GlideConfiguration
 * @Description TODO(这里用一句话描述这个类的作用)
 * @Date 2016年8月9日 下午1:51:59
 * @version 1.0.0
 */
public class GlideConfiguration implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Apply options to the builder here.
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // register ModelLoaders here.
    }
}
