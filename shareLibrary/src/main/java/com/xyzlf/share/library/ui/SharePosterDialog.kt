package com.xyzlf.share.library.ui

import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.xyzlf.share.library.R
import com.xyzlf.share.library.bean.ChannelEntity
import com.xyzlf.share.library.bean.ShareEntity
import com.xyzlf.share.library.util.ShareConstant
import com.xyzlf.share.library.view.FullScreenDialog
import java.util.*

/**
 * Fuction:分享海报
 * @author  Way Lo
 * @date 2019/7/15
 */
class SharePosterDialog(context: AppCompatActivity, data: ShareEntity) : ShareWxQq(context, data) {

    override fun initRootViewId(): Int {
        return R.layout.share_dialog_poster
    }

    override fun initOtherChannels() {
        (mListChannel as ArrayList<ChannelEntity>).add(ChannelEntity(ShareConstant.SHARE_CHANNEL_SYSTEM,
            R.drawable.share_more, context.getString(R.string.share_channel_save)))
    }

    override fun otherChannelsClick(item: ChannelEntity) {

    }

    override fun show() {
        mDialog?.run {
            if (isShowing) {
                dismiss()
            }
        }

        mDialog = FullScreenDialog(context)
        mDialog?.show()
        initOtherView()
        mDialog?.setContentView(mRootView!!)
    }

    private fun initOtherView() {
        mRootView?.findViewById<ImageView>(R.id.iv_preview)?.setImageResource(R.drawable.share_wechat)
    }

}