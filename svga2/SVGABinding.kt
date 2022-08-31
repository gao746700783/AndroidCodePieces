package com.example.myapplication.ui

import android.graphics.Color
import androidx.databinding.BindingAdapter
import com.opensource.svgaplayer.SVGAImageView
import com.opensource.svgaplayer.SVGADrawable

import android.text.TextPaint
import com.example.myapplication.ui.extension.SVGALoadHelper

import com.opensource.svgaplayer.SVGADynamicEntity

// 这种方式对应 java 中的 public static void ...
//object SVGABinding {

@BindingAdapter("svga_str")
fun svgaLoadFun(view: SVGAImageView?, svgaAssetsOrUrl: String) {
    SVGALoadHelper.getSingleton().load(view,svgaAssetsOrUrl)
}

@BindingAdapter(value = ["svga_str","svga_rep_text","svga_rep_key"], requireAll = true)
fun svgaLoadWithTextFun(view: SVGAImageView?, svgaAssetsOrUrl: String,repText :String,repKey:String) {

    SVGALoadHelper.getSingleton().loadWithCompleteCb(view,svgaAssetsOrUrl,
        SVGALoadHelper.OnCompleteCallback {
            val dynamicEntity = SVGADynamicEntity()
            val textPaint = TextPaint()
            textPaint.color = Color.WHITE
            textPaint.textSize = 28f
            dynamicEntity.setDynamicText(repText, textPaint, repKey)
            val drawable = SVGADrawable(it, dynamicEntity)
            view?.setImageDrawable(drawable)
            view?.startAnimation()
        })

}

//private fun loadSVGAFromUrl(
//    view: SVGAImageView?,
//    svgaAssetsOrUrl: String
//) {
//
////    view?.context?.let {
////        Glide
////            .with(it)
//////                    .asSVGA()
////            .load(svgaAssetsOrUrl)
////            .into(view)
////    }
//
//
//    SVGAParser.shareParser()
//        .decodeFromURL(URL(svgaAssetsOrUrl), object : SVGAParser.ParseCompletion {
//            override fun onComplete(videoItem: SVGAVideoEntity) {
////                val density: Float = ScreenUtils.getDensity(view?.context)
//                val density: Float = 2.75f
//                var total = fetchTotalMemSize(videoItem,density)
//                if (total >= 10 * 1024 * 1024) {
////                    val firstFrame = fetchFirstFrame(videoItem)
//                    view?.context?.let {
////                        GlideApp.with(it).load(firstFrame).into(view)
//                        videoItem.clear()
//                        return;
//                    }
//                }
//
//                view?.setVideoItem(videoItem)
//                view?.stepToFrame(0, true)
//            }
//
//            override fun onError() {
//            }
//        })
//
//}
//
//private fun loadSVGAFromAssets(
//    view: SVGAImageView?,
//    svgaAssetsOrUrl: String
//) {
//
////    view?.context?.let {
////        GlideApp
////            .with(it)
//////            .asSVGA()
////            .load("file:///android_asset/$svgaAssetsOrUrl")
////            .into(view)
////    }
//
//    SVGAParser.shareParser()
//        .decodeFromAssets(svgaAssetsOrUrl, object : SVGAParser.ParseCompletion {
//            override fun onComplete(videoItem: SVGAVideoEntity) {
//                val density = ScreenUtils.getDensity(view?.context)
//                var total = fetchTotalMemSize(videoItem,density)
//                Timber.d("totalMemSize:$total")
//
//                if (total >= 10 * 1024 * 1024) {
//                    val firstFrame = fetchFirstFrame(videoItem)
//                    view?.context?.let {
//                        //GlideApp.with(it).asBitmap().load(firstFrame).into(view)
//                        return;
//                    }
//                }
//                view?.setVideoItem(videoItem)
//                view?.stepToFrame(0, true)
//            }
//
//            override fun onError() {
//            }
//        })
//
//}
//
//
//private fun fetchTotalMemSize(videoItem: SVGAVideoEntity, density: Float): Long {
//    val imagesFiled: Field = videoItem.javaClass.getDeclaredField("imageMap")
//    imagesFiled.isAccessible = true
//    val images: HashMap<String, Bitmap> = imagesFiled.get(videoItem) as HashMap<String, Bitmap>
//    Timber.d("images.size : " + images.size + ",density:" + density)
//    var total = 0L
//
//    for (entry in images) {
//        var temp: Bitmap = entry.value
//
//        var height = temp.height * density.toInt()
//        var width = temp.width * density.toInt()
//
//        Timber.d("height:$height,width:$width")
//        total += height * width * 4
//    }
//    Timber.d("totalMemSize:$total" )
//
//    return total
//}
//
//private fun fetchFirstFrame(videoItem: SVGAVideoEntity): Bitmap? {
//    val imagesFiled: Field = videoItem.javaClass.getDeclaredField("imageMap")
//    imagesFiled.isAccessible = true
//    val images: HashMap<String, Bitmap> =
//        imagesFiled.get(videoItem) as HashMap<String, Bitmap>
//    var result: Bitmap? = null
//    for (entry in images) {
//        result = entry.value
//    }
//    return result
//}

//}