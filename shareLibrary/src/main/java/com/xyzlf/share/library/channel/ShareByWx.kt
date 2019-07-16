package com.xyzlf.share.library.channel

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
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
 * @date 2019/7/15
 */
class ShareByWx(context: AppCompatActivity, data: ShareEntity, val channel: Int) : ShareBase(context, data) {

    override fun share(data: ShareEntity, listener: OnShareListener?) {
        ImageUrisAsyncTask(mData.imgUrls, object : OnDownloadListener<ArrayList<Uri>> {
            override fun onSuccess(t: ArrayList<Uri>) {
                shareWx(t)
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

    private fun shareWx(imageUris: ArrayList<Uri>) {
        if (channel == ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND) {
            shareWxSession(imageUris)
        } else {
            shareWxTimeLine(imageUris)
        }
    }

    /**
     * 分享微信好友
     */
    private fun shareWxSession(imageUris: ArrayList<Uri>) {
        ShareUtil.share(mContext, imageUris, ShareConstant.WEIXIN_PACKAGE_NAME, "com.tencent.mm.ui.tools.ShareImgUI")
    }

    /**
     * 分享图片到微信朋友圈，微信7.0以下版本可分享多张图片，7.0以上版本只可分享一张图片
     *
     *
     * https://blog.csdn.net/okg0111/article/details/86498186
     *
     * @param imageUris 图片集合
     */
    private fun shareWxTimeLine(imageUris: ArrayList<Uri>) {
        if (imageUris.isEmpty()) {
            return
        }
        val intent = Intent()
        val comp = ComponentName(ShareConstant.WEIXIN_PACKAGE_NAME, "com.tencent.mm.ui.tools" + ".ShareToTimeLineUI")
        intent.component = comp
        /**
         * 微信7.0版本号，兼容处理微信7.0版本分享到朋友圈不支持多图片的问题
         */
        val versionCodeForWxVer7 = 1380
        if (ShareUtil.getAppVersionCode(mContext, ShareConstant.WEIXIN_PACKAGE_NAME) < versionCodeForWxVer7) {
            // 微信7.0以下版本
            intent.action = Intent.ACTION_SEND_MULTIPLE
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
        } else {
            // 微信7.0及以上版本
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_STREAM, imageUris[0])
        }
        intent.type = "image/*"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        mContext.startActivity(intent)
    }

}