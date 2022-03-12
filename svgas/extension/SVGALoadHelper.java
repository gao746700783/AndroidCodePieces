package com.example.myapplication.extension;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.opensource.svgaplayer.interceptor.MaxSizeInterceptor;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import timber.log.Timber;

public class SVGALoadHelper {

    private static final String TAG = SVGALoadHelper.class.getSimpleName();

    private volatile static SVGALoadHelper singleton;

    private static final long MAX_SVGA_SIZE = 10 * 1024 * 1024;

    private SVGALoadHelper() {
        SVGAParser.Companion.shareParser().addInterceptors(new MaxSizeInterceptor(MAX_SVGA_SIZE));

    }

    public static SVGALoadHelper getSingleton() {
        if (singleton == null) {
            synchronized (SVGALoadHelper.class) {
                if (singleton == null) {
                    singleton = new SVGALoadHelper();
                }
            }
        }
        return singleton;
    }

    public void loadWithCompleteCb(SVGAImageView target,String assetOrUrl,OnCompleteCallback onCompleteCb) {
        load(target, assetOrUrl,null,onCompleteCb);
    }
    public void load(SVGAImageView target,String assetOrUrl) {
        load(target, assetOrUrl,null);
    }

    public void load(SVGAImageView target, String assetOrUrl, SVGACallback callback) {
        load(target, assetOrUrl, callback,null);
    }

    public void load(SVGAImageView target, String assetOrUrl, SVGACallback callback,OnCompleteCallback onCompleteCb) {
        if (null == target) {
            Timber.e("target is null");
            throw new NullPointerException();
        }
        if (TextUtils.isEmpty(assetOrUrl)) {
            Timber.e("assetOrUrl is empty");
            return;
        }

        SVGAParser.ParseCompletion parseCompletion = new SVGAParser.ParseCompletion() {

            @Override
            public void onComplete(SVGAVideoEntity svgaVideoEntity) {
                if (svgaVideoEntity.getIntercept()) {
                    Glide.with(target.getContext()).load(R.mipmap.ic_launcher).into(target);
                    return;
                }

                if (null != onCompleteCb) {
                    onCompleteCb.onComplete(svgaVideoEntity);
                    return;
                }

                target.setVideoItem(svgaVideoEntity);
                target.stepToFrame(0,true);
            }

            @Override
            public void onError() {

            }
        };

        SVGAParser.PlayCallback playCallback = new SVGAParser.PlayCallback() {
            @Override
            public void onPlay( List<? extends File> list) {
            }
        };


        if (assetOrUrl.startsWith("http:") || assetOrUrl.startsWith("https:")) {
            loadSVGAFromUrl(target, assetOrUrl, callback,parseCompletion,playCallback);
            return;
        }

        loadSVGAFromAssets(target, assetOrUrl, callback,parseCompletion,playCallback);
    }

    private void loadSVGAFromUrl(SVGAImageView target, String assetOrUrl, SVGACallback callback
            ,SVGAParser.ParseCompletion parseCompletion
            ,SVGAParser.PlayCallback playCallback
    ) {
        try {
            SVGAParser.Companion.shareParser().decodeFromURL(new URL(assetOrUrl),
                    parseCompletion, playCallback);
            if (null != callback) {
                target.setCallback(callback);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSVGAFromAssets(SVGAImageView target, String assetOrUrl, SVGACallback callback
            ,SVGAParser.ParseCompletion parseCompletion
            ,SVGAParser.PlayCallback playCallback
    ) {
        try {
            SVGAParser.Companion.shareParser().decodeFromAssets(assetOrUrl,
                    parseCompletion, playCallback);
            if (null != callback) {
                target.setCallback(callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface OnCompleteCallback {
        void onComplete(SVGAVideoEntity svgaVideoEntity);
    }


}
