//package com.example.myapplication.extension;
//
//import android.text.TextUtils;
//
//import com.opensource.svgaplayer.SVGACallback;
//import com.opensource.svgaplayer.SVGAImageView;
//import com.opensource.svgaplayer.SVGAParser;
//import com.opensource.svgaplayer.SVGAVideoEntity;
//
//import java.io.File;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.List;
//
//import timber.log.Timber;
//
//public class SVGALoadHelperV1 {
//
//    private static final String TAG = SVGALoadHelperV1.class.getSimpleName();
//
//    private volatile static SVGALoadHelperV1 singleton;
//
//    private SVGALoadHelperV1() {
//    }
//
//    public static SVGALoadHelperV1 getSingleton() {
//        if (singleton == null) {
//            synchronized (SVGALoadHelperV1.class) {
//                if (singleton == null) {
//                    singleton = new SVGALoadHelperV1();
//                }
//            }
//        }
//        return singleton;
//    }
//
//    private static final long MAX_SVGA_SIZE = 20 * 1024 * 1024;
//
//    public void load(SVGAImageView target,String assetOrUrl) {
//        load(target, assetOrUrl,null);
//    }
//
//    public void load(SVGAImageView target, String assetOrUrl, SVGACallback callback) {
//        if (null == target) {
//            Timber.e("target is null");
//            throw new NullPointerException();
//        }
//        if (TextUtils.isEmpty(assetOrUrl)) {
//            Timber.e("assetOrUrl is empty");
//            return;
//        }
//
//        SVGAParser.ParseCompletion parseCompletion = new SVGAParser.ParseCompletion() {
//            @Override
//            public boolean onPreHandle( SVGAVideoEntity svgaVideoEntity) {
//                if (svgaVideoEntity!= null && svgaVideoEntity.getPreCalcSize() > MAX_SVGA_SIZE /2) {
////                    Glide.with(target.getContext()).load(R.mipmap.ic_launcher).into(target);
//                    return true;
//                }
//                return false;
//            }
//
//            @Override
//            public void onComplete( SVGAVideoEntity svgaVideoEntity) {
//                target.setVideoItem(svgaVideoEntity);
//                target.stepToFrame(0,true);
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        };
//
//        SVGAParser.PlayCallback playCallback = new SVGAParser.PlayCallback() {
//            @Override
//            public void onPlay( List<? extends File> list) {
//            }
//        };
//
//
//        if (assetOrUrl.startsWith("http:") || assetOrUrl.startsWith("https:")) {
//            loadSVGAFromUrl(target, assetOrUrl, callback,parseCompletion,playCallback);
//            return;
//        }
//
//        loadSVGAFromAssets(target, assetOrUrl, callback,parseCompletion,playCallback);
//    }
//
//    private void loadSVGAFromUrl(SVGAImageView target, String assetOrUrl, SVGACallback callback
//            ,SVGAParser.ParseCompletion parseCompletion
//            ,SVGAParser.PlayCallback playCallback
//    ) {
//        try {
//            SVGAParser.Companion.shareParser().decodeFromURL(new URL(assetOrUrl),
//                    parseCompletion, playCallback);
//            if (null != callback) {
//                target.setCallback(callback);
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void loadSVGAFromAssets(SVGAImageView target, String assetOrUrl, SVGACallback callback
//            ,SVGAParser.ParseCompletion parseCompletion
//            ,SVGAParser.PlayCallback playCallback
//    ) {
//        try {
//            SVGAParser.Companion.shareParser().decodeFromAssets(assetOrUrl,
//                    parseCompletion, playCallback);
//            if (null != callback) {
//                target.setCallback(callback);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
