package com.xyzlf.share.library.view

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import com.xyzlf.share.library.R

/**
 * Fuction:全屏 Dialog，先调用 show()，再调用 setContentView()
 *
 * @author Way Lo
 * @date 2019/7/16
 */
class FullScreenDialog(private val mContext: Context) : AlertDialog(mContext, R.style.ActionSheetDialogStyle) {

    override fun show() {
        super.show()
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        val layoutParams = window!!.attributes
        layoutParams.gravity = Gravity.BOTTOM
        layoutParams.width = LayoutParams.MATCH_PARENT
        layoutParams.height = LayoutParams.MATCH_PARENT
        window!!.decorView.setPadding(0, 0, 0, 0)
        window!!.attributes = layoutParams
    }
}
