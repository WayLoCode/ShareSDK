package com.xyzlf.share.library.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Display
import android.view.Gravity
import android.view.WindowManager

import com.xyzlf.share.library.R

/**
 * Fuction: 底部弹出 Dialog
 *
 * @author Way Lo
 * @date 2019/7/16
 */
class BottomDialog(context: Context) : Dialog(context, R.style.ActionSheetDialogStyle) {

    private var mDisPlay: Display

    init {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mDisPlay = windowManager.defaultDisplay
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dialogWindow = window
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.BOTTOM)
            val lp = dialogWindow.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.x = 0
            lp.y = 0
            dialogWindow.attributes = lp
        }
        setCancelable(true)
        setCanceledOnTouchOutside(true)
    }

}
