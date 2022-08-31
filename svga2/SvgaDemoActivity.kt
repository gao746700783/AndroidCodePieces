package com.example.myapplication.ui

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_archmvvm.ui.base.BaseVMActivity
import com.example.lib_archmvvm.vm.base.BaseViewModel
import com.example.lib_base.utils.KLog
import com.example.myapplication.BR
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySvgaDemoBinding
import com.opensource.svgaplayer.SVGAImageView
import com.smart.adapter.rvbinding.BindingViewHolder
import com.smart.adapter.rvbinding.CommonBindingAdapter
import com.smart.adapter.rvbinding.IConverter
import com.smart.adapter.rvbinding.IHolder

class SvgaDemoActivity : BaseVMActivity<ActivitySvgaDemoBinding, SVGADemoViewModel>() {

    override fun initContentView(): Int = R.layout.activity_svga_demo

    override fun initViewModel(): SVGADemoViewModel =
        BaseViewModel.newVmInstance(this, SVGADemoViewModel::class.java)

    override fun initVariableId(): Int = BR.svgaDemoVM

    var mAdapter: CommonBindingAdapter<String>? = null
    var mRv: RecyclerView? = null

    var mScrolling = false

    override fun initAfter() {
        super.initAfter()

        mRv = viewDataBinding.rvSVGAS
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRv?.layoutManager = mLayoutManager

        val itemDecoration = DividerItemDecoration(this, mLayoutManager.orientation)
        mRv?.addItemDecoration(itemDecoration)

        mAdapter = object : CommonBindingAdapter<String>(this) {
            override fun onViewAttachedToWindow(holder: BindingViewHolder<*>) {
                super.onViewAttachedToWindow(holder)
                holder.getView<SVGAImageView>(R.id.svgaImage).startAnimation()
            }

            override fun onViewDetachedFromWindow(holder: BindingViewHolder<*>) {
                super.onViewDetachedFromWindow(holder)
                if (holder.getView<SVGAImageView>(R.id.svgaImage).isAnimating) {
                    holder.getView<SVGAImageView>(R.id.svgaImage).stopAnimation()
                }
            }
        }
            .layout(R.layout.layout_list_item_rv)
            .list(viewModel.svgaAssetsList.value)
            .bindViewAndData(object : IConverter<String?> {
                override fun convert(holder: IHolder, item: String?, position: Int) {
                    val svga = holder.getView<SVGAImageView>(R.id.svgaImage)
                    svga.tag = item


                }

                override fun getVariableId(viewType: Int): Int {
                    return BR.testVM
                }
            })
        mRv?.adapter = mAdapter

        mRv?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mScrolling = newState != RecyclerView.SCROLL_STATE_IDLE
                KLog.d("scrolling :$mScrolling")
            }
        })
    }
}