package com.xyzlf.share.library.ui

import android.app.Dialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import com.xyzlf.share.library.R
import com.xyzlf.share.library.adapter.AppGridAdapter
import com.xyzlf.share.library.bean.ChannelEntity
import com.xyzlf.share.library.bean.ShareEntity
import com.xyzlf.share.library.channel.ShareByQq
import com.xyzlf.share.library.channel.ShareByWx
import com.xyzlf.share.library.util.ChannelUtil
import com.xyzlf.share.library.util.ShareConstant
import com.xyzlf.share.library.view.BottomDialog
import java.util.*

/**
 * Fuction: 微信、QQ分享
 * @author  Way Lo
 * @date 2019/7/16
 */
open class ShareWxQq(var context: AppCompatActivity, var data: ShareEntity) {

    protected var mListChannel: List<ChannelEntity> = ArrayList()
    protected var mDialog: Dialog? = null
    protected var mRootView: View? = null

    init {
        initView()
    }

    private fun initView() {
        mRootView = LayoutInflater.from(context).inflate(initRootViewId(), null)

        initCommonChannels(context)
        initOtherChannels()

        val adapter = AppGridAdapter(context, mListChannel)
        val shareGridView = mRootView?.findViewById<View>(R.id.share_grid) as GridView
        shareGridView.adapter = adapter
        shareGridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> share(mListChannel[position]) }
    }

    protected open fun initRootViewId(): Int {
        return R.layout.share_dialog_link
    }

    protected open fun initOtherChannels() {

    }

    protected open fun otherChannelsClick(item: ChannelEntity) {

    }

    open fun show() {
        mDialog?.run {
            if (isShowing) {
                dismiss()
            }
        }

        mDialog = BottomDialog(context)
        mDialog?.setContentView(mRootView!!)
        mDialog?.show()
    }

    protected fun initCommonChannels(context: Context) {
        /** weixin  */
        if (ChannelUtil.isWeixinInstall(context)) {
            (mListChannel as ArrayList<ChannelEntity>).add(ChannelEntity(ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND,
                R.drawable.share_wechat, context.getString(R.string.share_channel_weixin_friend)))

            (mListChannel as ArrayList<ChannelEntity>).add(ChannelEntity(ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE,
                R.drawable.share_wxcircle, context.getString(R.string.share_channel_weixin_circle)))
        }
        /** QQ  */
        if (ChannelUtil.isQQInstall(context)) {
            (mListChannel as ArrayList<ChannelEntity>).add(ChannelEntity(ShareConstant.SHARE_CHANNEL_QQ,
                R.drawable.share_qq, context.getString(R.string.share_channel_qq)))

            (mListChannel as ArrayList<ChannelEntity>).add(ChannelEntity(ShareConstant.SHARE_CHANNEL_QZONE,
                R.drawable.share_qzone, context.getString(R.string.share_channel_qzone)))
        }
    }

    protected fun share(item: ChannelEntity) {
        when (item.channel) {
            ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND, ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE -> {
                ShareByWx(context, data, item.channel).share()
            }
            ShareConstant.SHARE_CHANNEL_QQ, ShareConstant.SHARE_CHANNEL_QZONE -> {
                ShareByQq(context, data, item.channel).share()
            }
            else -> {
                otherChannelsClick(item)
            }
        }
    }
}