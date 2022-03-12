package com.example.myapplication.ui

import androidx.lifecycle.MutableLiveData
import com.example.lib_archmvvm.vm.base.BaseViewModel

class SVGADemoViewModel : BaseViewModel() {

    //生命周期观察的数据
    var mTestData = MutableLiveData<String>()

    val svgaAssetsList = MutableLiveData<ArrayList<String>>()
    init {
        mTestData.value = "initial value"
        var list = ArrayList<String>()
//        list.add("svgs/EmptyState.svga")
//        list.add("svgs/HamburgerArrow.svga")
//        list.add("svgs/HamburgerArrow.svga")
//        list.add("svgs/heartbeat.svga")
//        list.add("svgs/kingset.svga")
//        list.add("svgs/matteBitmap.svga")
//        list.add("svgs/matteRect.svga")
//        list.add("svgs/mutiMatte.svga")
//        list.add("svgs/PinJump.svga")
//        list.add("svgs/posche.svga")
//        list.add("svgs/Rocket.svga")
//        list.add("svgs/TwitterHeart.svga")
//        list.add("svgs/Walkthrough.svga")

        list.add("https://alioss.enuos.club/file/admin/b6f5b6cb6eb04ce0969bb6c0ce6dd65b.svga")
        list.add("https://alioss.enuos.club/file/admin/43819933370c481d881882eb470d528b.svga")
        list.add("https://alioss.enuos.club/file/admin/e6f87eb59a884da3bfacca307ef49f2f.svga")
        list.add("https://alioss.enuos.club/file/admin/cf8cd857c6354416af42cab78c4f020b.svga")
        list.add("https://alioss.enuos.club/file/admin/2fdebc846d4a46d6a9df42225ad7f9c9.svga")
        list.add("https://alioss.enuos.club/file/admin/64a3af239c304b35b9b2a1c6938d15c1.svga")
        list.add("https://alioss.enuos.club/file/admin/013bd587081e4a76b32bc575c439425a.svga")
        list.add("https://alioss.enuos.club/file/admin/dbbe7d7070b4478c9324947bb43b0455.svga")
        list.add("https://alioss.enuos.club/file/admin/9427923a68444334810ab5b7acf2cf11.svga")
        list.add("https://alioss.enuos.club/file/admin/4b9f26e96f384086a164c595202c6815.svga")
        list.add("https://alioss.enuos.club/file/admin/36fdc534832b4948b9fb3bee9b16dc27.svga")


        svgaAssetsList.value = list
    }

    companion object {
        private const val TAG = "SVGADemoViewModel"
    }

}