package com.example.myapplication.extension;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

@GlideModule
public class MyGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        /*
         * MemorySizeCalculator类通过考虑设备给定的可用内存和屏幕大小想出合理的默认大小.
         * 通过LruResourceCache进行缓存
         */

        // 设置内存缓存大小 // FIXME: 2021/11/2 trim时出现NoSuchElementException
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(1)
                .setMaxSizeMultiplier(0.25f)            // 最多使用app可用内存的 1/4
                .setLowMemoryMaxSizeMultiplier(0.125f)  // 低内存设备，最多使用app可用内存的 1/8
                .build();
        builder.setMemoryCache(new LruResourceCache((int)(calculator.getMemoryCacheSize() * 0.8)));

        // 设置缓存大小为100mb
        int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));

        builder.setBitmapPool(new LruBitmapPool((int) (calculator.getBitmapPoolSize() * 0.6)));
        RequestOptions requestOptions = new RequestOptions()
                .fitCenter()
//                .placeholder(R.drawable.default_placehold)
                .format(DecodeFormat.PREFER_RGB_565)
//                .sizeMultiplier(0.7f);
        ;
        builder.setDefaultRequestOptions(requestOptions);

    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

}