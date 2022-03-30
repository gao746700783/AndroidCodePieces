// region fix android Q  IRequestFinishCallback$Stub内存泄露问题 https://juejin.cn/post/6920466432648019982

private var fallbackOnBackPressed: Runnable? = null

companion object {
    @JvmStatic
    val fallbackOnBackPressedField by lazy {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.Q) {
            null
        } else {
            // OnBackPressedDispatcher 中只有fallbackOnBackPressed是Runnable
            // 这样可以避免代码混淆带来的影响
            OnBackPressedDispatcher::class.java.declaredFields.find {
                it.type.isAssignableFrom(Runnable::class.java)
            }
        }
    }
}

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    fixAndroidQMemoryLeak()
}

private fun fixAndroidQMemoryLeak() {
    if (Build.VERSION.SDK_INT != Build.VERSION_CODES.Q) return
    fallbackOnBackPressedField?.runCatching {
        isAccessible = true
        // 缓存默认的 fallbackOnBackPressed，如果不是TaskRoot还可以直接用它
        fallbackOnBackPressed = get(onBackPressedDispatcher) as? Runnable
        if (fallbackOnBackPressed != null) {
            // 替换默认的 fallbackOnBackPressed
            set(onBackPressedDispatcher, Runnable {
                onFallbackOnBackPressed()
            })
        }
        fallbackOnBackPressedField?.isAccessible = false
    }?.onFailure {
        fallbackOnBackPressedField?.isAccessible = false
    }
}

/**
 * FragmentActivity等都是利用[mOnBackPressedDispatcher]addCallback完成Fragment pop，
 * 并且会优先于[OnBackPressedDispatcher]#mFallbackOnBackPressed执行
 */
@RequiresApi(Build.VERSION_CODES.Q)
private fun onFallbackOnBackPressed() {
    // 如果不是TaskRoot，不存在内存泄漏，执行原有函数就可以
    if (!isTaskRoot) {
        fallbackOnBackPressed?.run()
        return
    }
    // actionBar#collapseActionView 属于私有函数，所以就不要在Activity中使用[android.app.ActionBar]
    // if (actionBar != null && actionBar.collapseActionView()) return
    if (!fragmentManager.isStateSaved && fragmentManager.popBackStackImmediate()) return
    finishAfterTransition()
}
// endregion