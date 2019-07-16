package com.xyzlf.share.library.channel

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.xyzlf.share.library.bean.ShareEntity
import com.xyzlf.share.library.interfaces.OnDownloadListener
import com.xyzlf.share.library.interfaces.OnShareListener
import com.xyzlf.share.library.request.ImageUrisAsyncTask
import com.xyzlf.share.library.util.ShareConstant
import com.xyzlf.share.library.util.ShareUtil
import com.xyzlf.share.library.util.ToastUtil
import java.util.*


/**
 * Fuction:
 * @author  Way Lo
 * @date 2019/7/16
 */
class ShareByQq(context: AppCompatActivity, data: ShareEntity, val channel: Int) : ShareBase(context, data) {

    override fun share(data: ShareEntity, listener: OnShareListener?) {
        ImageUrisAsyncTask(mData.imgUrls, object : OnDownloadListener<ArrayList<Uri>> {
            override fun onSuccess(t: ArrayList<Uri>) {
                share(t)
            }

            override fun onFail(exception: Exception?) {
                ToastUtil.showToast(mContext, com.xyzlf.share.library.R.string.share_save_bitmap_failed, true)
            }
        }).execute()
    }

    @SuppressLint("CheckResult")
    fun share() {
        val rxPermissions = RxPermissions(mContext as FragmentActivity)
        rxPermissions
            .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe { granted ->
                if (granted) {
                    // All requested permissions are granted
                    this.share(mData, null)
                } else {
                    // At least one permission is denied
                    mContext.finish()
                }
            }
    }

    private fun share(imageUris: ArrayList<Uri>) {
        if (channel == ShareConstant.SHARE_CHANNEL_QQ) {
            shareQqSession(imageUris)
        } else {
            shareQzone(imageUris)
        }
    }

    /**
     * 分享QQ好友
     */
    private fun shareQqSession(imageUris: ArrayList<Uri>) {
        ShareUtil.share(mContext, imageUris, ShareConstant.QQ_PACKAGE_NAME, "com.tencent.mobileqq.activity.JumpActivity")
    }

    /**
     * 分享QQ空间
     */
    private fun shareQzone(imageUris: ArrayList<Uri>) {
        ShareUtil.share(mContext, imageUris, ShareConstant.QQ_ZONE_PACKAGE_NAME, "com.qzonex.module.operation.ui.QZonePublishMoodActivity")
    }

}