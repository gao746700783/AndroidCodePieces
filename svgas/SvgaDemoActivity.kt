package com.example.myapplication.ui

import com.example.lib_archmvvm.ui.base.BaseVMActivity
import com.example.lib_archmvvm.vm.base.BaseViewModel
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySvgaDemoBinding

class SvgaDemoActivity : BaseVMActivity<ActivitySvgaDemoBinding, SVGADemoViewModel>() {

    override fun initContentView(): Int = R.layout.activity_svga_demo

    override fun initViewModel() :SVGADemoViewModel =
        BaseViewModel.newVmInstance(this, SVGADemoViewModel::class.java)

    override fun initVariableId(): Int = BR.svgaDemoVM

}