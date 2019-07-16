package com.xyzlf.share.library

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.xyzlf.share.library.bean.ShareEntity
import com.xyzlf.share.library.ui.*
import com.xyzlf.share.library.util.ShareConstant
import com.xyzlf.share.library.util.ShareUtil

/**
 * Created by zhanglifeng on 15/6/4.
 */
object ShareHelper {

    /**
     * 分享数据
     *
     * @param activity    AppCompatActivity
     * @param channel     [ShareConstant]
     * @param data        [ShareEntity]
     * @param requestCode
     */
    fun startShare(activity: AppCompatActivity?, channel: Int, data: ShareEntity,
                   requestCode: Int) {
        if (null == activity || activity.isFinishing) {
            return
        }
        val intent = Intent(activity, ShareHandlerActivity::class.java)
        intent.putExtra(ShareConstant.EXTRA_SHARE_CHANNEL, channel)
        intent.putExtra(ShareConstant.EXTRA_SHARE_DATA, data)
        activity.startActivityForResult(intent, requestCode)
    }

    /**
     * 调起ShareDialogActivity
     *
     * @param activity    AppCompatActivity
     * @param data        [ShareEntity]
     * @param requestCode requestCode
     */
    fun showShareDialog(activity: AppCompatActivity, data: ShareEntity, requestCode: Int) {
        showShareDialog(activity, ShareConstant.SHARE_CHANNEL_ALL, data, requestCode)
    }

    /**
     * 调起ShareDialogActivity
     *
     * @param activity    AppCompatActivity
     * @param data        [ShareEntity]
     * @param channel     [ShareConstant.SHARE_CHANNEL_ALL]
     * @param requestCode requestCode
     */
    fun showShareDialog(activity: AppCompatActivity?, channel: Int, data: ShareEntity,
                        requestCode: Int) {
        if (null == activity || activity.isFinishing) {
            return
        }
        val intent = Intent(activity, ShareDialogActivity::class.java)
        intent.putExtra(ShareConstant.EXTRA_SHARE_DATA, data)
        intent.putExtra(ShareConstant.EXTRA_SHARE_CHANNEL, channel)
        activity.startActivityForResult(intent, requestCode)
    }

    fun showShareWxQqLinkDialog(activity: AppCompatActivity?, data: ShareEntity) {
        if (null == activity || activity.isFinishing) {
            return
        }

        val dialog = ShareLinkDialog(activity, data)
        dialog.show()
    }

    fun showShareWxQqImageDialog(activity: AppCompatActivity?, data: ShareEntity) {
        if (null == activity || activity.isFinishing) {
            return
        }

        val dialog = ShareImageDialog(activity, data)
        dialog.show()
    }

    fun showShareWxQqPosterDialog(activity: AppCompatActivity?, data: ShareEntity) {
        if (null == activity || activity.isFinishing) {
            return
        }

        val dialog = SharePosterDialog(activity, data)
        dialog.show()
    }

    fun startThirdActivity(context: Context, intent: Intent): Boolean {
        return ShareUtil.startActivity(context, intent)
    }
}
