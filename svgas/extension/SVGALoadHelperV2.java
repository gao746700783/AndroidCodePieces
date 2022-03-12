//package com.example.myapplication.extension;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.text.TextUtils;
//import android.util.DisplayMetrics;
//
//import com.opensource.svgaplayer.SVGACallback;
//import com.opensource.svgaplayer.SVGAImageView;
//import com.opensource.svgaplayer.SVGAParser;
//import com.opensource.svgaplayer.SVGAVideoEntity;
//
//import java.io.File;
//import java.lang.reflect.Field;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.List;
//
//import timber.log.Timber;
//
//public class SVGALoadHelperV2 {
//
//    private static final String TAG = SVGALoadHelper.class.getSimpleName();
//
//    private volatile static SVGALoadHelperV2 singleton;
//
//    private SVGALoadHelperV2() {
//    }
//
//    public static SVGALoadHelperV2 getSingleton() {
//        if (singleton == null) {
//            synchronized (SVGALoadHelperV2.class) {
//                if (singleton == null) {
//                    singleton = new SVGALoadHelperV2();
//                }
//            }
//        }
//        return singleton;
//    }
//
//    private static final long MAX_SVGA_SIZE = 20 * 1024 * 1024;
//
//    // 是否自动排雷，排除占用内存过大的动图
//    private boolean isAutoDemining = true;
//    public void enableAutoDemining (boolean autoDemining) {
//        this.isAutoDemining = autoDemining;
//    }
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
//
//            @Override
//            public void onComplete( SVGAVideoEntity svgaVideoEntity) {
//                if (isAutoDemining) {
//                    float density = getDensity(target.getContext());
////                    float density = 2.75f;
//                    Bitmap firstFrame = fetchFirstFrame(svgaVideoEntity,density);
//                    if (null != firstFrame) {
//                        Timber.d("svga too large,load first frame");
//                        GlideApp.with(target.getContext()).load(firstFrame).into(target);
//                        svgaVideoEntity.clear();
//                        return;
//                    }
//
//                    long total = fetchTotalMemSize(svgaVideoEntity,density);
//                    if (total > MAX_SVGA_SIZE) {
//                        Timber.d("svga too large,clear and do nothing");
//                        svgaVideoEntity.clear();
//                        return;
//                    }
//                }
//
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
//    private Bitmap fetchFirstFrame(SVGAVideoEntity videoItem,float density) {
//        Bitmap firstFrame = null;
//
//        try {
//            Field imagesField = videoItem.getClass().getDeclaredField("imageMap");
//            imagesField.setAccessible(true);
//            HashMap<String, Bitmap> images = (HashMap<String, Bitmap>) imagesField.get(videoItem);
//
//            Timber.d("images.size:%s,density:%f", images.size(),density);
//
//            long total = -1L;// 如果最后结果返回-1，表示计算过程出现异常
//
//            for (Bitmap temp : images.values()) {
//                Timber.d("ori_height:" + temp.getHeight()+",ori_width:" + temp.getWidth());
//                int height = (int) (temp.getHeight() * density);
//                int width = (int) (temp.getWidth() * density);
//                Timber.d("height:" + height+",width:" + width);
//                total += height * width * 4;
//            }
//
//            Timber.d("totalMemSize:" +total );
//
//            if (total > MAX_SVGA_SIZE) {
//                for (Bitmap temp : images.values()) {
//                    firstFrame = temp;
//                    break;
//                }
//            }
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e){
//            e.printStackTrace();
//        }
//
//        return firstFrame;
//    }
//
//    private long fetchTotalMemSize(SVGAVideoEntity videoItem,float density) {
//        long total = -1L;// 如果最后结果返回-1，表示计算过程出现异常
//        try {
//            Field imagesField = videoItem.getClass().getDeclaredField("imageMap");
//            imagesField.setAccessible(true);
//            HashMap<String, Bitmap> images = (HashMap<String, Bitmap>) imagesField.get(videoItem);
//
//            Timber.d("images.size:%s", images.size());
//
//            for (Bitmap temp : images.values()) {
//                int height = (int) (temp.getHeight() * density);
//                int width = (int) (temp.getWidth() * density);
//                Timber.d("height:"+height+",width:" +width);
//                total += height * width * 4;
//            }
//
//            Timber.d("totalMemSize:" +total );
//
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e){
//            e.printStackTrace();
//        }
//
//        return total;
//    }
//
//
//    public static float getDensity(Context context) {
//        DisplayMetrics metric = context.getResources().getDisplayMetrics();
//        return metric.density;
//    }
//
//}
