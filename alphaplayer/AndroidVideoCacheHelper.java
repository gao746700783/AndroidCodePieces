AlphaPlayerManager.get().init(this@MainActivity,this@MainActivity,mViewDataBinding.rlVideo)
                val url = "https://alioss.suishoubo123.com/file/admin/1969e24cf26e4d36bcc12063dc95a379.mp4"
                //val url = "http://xychead.xueyiche.vip/fx_shipin1654764335491.mp4"
                val proxyUrl = AndroidVideoCacheHelper.get().getProxyUrl(url)
                //val proxyUrl = this@MainActivity.getExternalFilesDir("")?.absolutePath + "/demo_video.mp4"
                AlphaPlayerManager.get().startAlphaPlayer(proxyUrl)


                        AndroidVideoCacheHelper.get().init(this)


<FrameLayout
                android:id="@+id/rl_video"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                tools:ignore="MissingConstraints" />


    implementation 'io.github.gao746700783:alpha-player:0.0.2'
    implementation 'com.danikula:videocache:2.7.1'


    package com.example.myapplication;

import android.content.Context;
import android.net.Uri;

import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.file.FileNameGenerator;

import java.io.File;

public class AndroidVideoCacheHelper {

    private static final String TAG = "AlphaPlayerManager";

    private AndroidVideoCacheHelper() {
    }

    private static class Instance {
        private static final AndroidVideoCacheHelper INSTANCE = new AndroidVideoCacheHelper();
    }

    public static AndroidVideoCacheHelper get() {
        return AndroidVideoCacheHelper.Instance.INSTANCE;
    }

    private HttpProxyCacheServer proxy;

    public void init(Context context){
        Context appContext = context.getApplicationContext();

        File externalCacheDir = appContext.getExternalCacheDir();
        File videoCacheDir = new File(appContext.getExternalFilesDir(null),"video-cache");
        proxy = new HttpProxyCacheServer.Builder(appContext)
                .cacheDirectory(videoCacheDir)
                .maxCacheFilesCount(50)
                .maxCacheSize(500 * 1024 *1024)
                .build();

    }

    public String getProxyUrl(String oriUrl){
        return proxy.getProxyUrl(oriUrl);
    }

    // custom you own filename generator
    public class MyFileNameGenerator implements FileNameGenerator {
        // Urls contain mutable parts (parameter 'sessionToken') and stable video's id (parameter 'videoId').
        // e. g. http://example.com?videoId=abcqaz&sessionToken=xyz987
        public String generate(String url) {
            Uri uri = Uri.parse(url);
            String videoId = uri.getQueryParameter("videoId");
            return videoId + ".mp4";
        }
    }

}
