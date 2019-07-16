package com.xyzlf.share.library.ui

import android.support.v7.app.AppCompatActivity
import com.xyzlf.share.library.R
import com.xyzlf.share.library.bean.ChannelEntity
import com.xyzlf.share.library.bean.ShareEntity
import com.xyzlf.share.library.util.ShareConstant
import java.util.*

/**
 * Fuction:分享图片
 * @author  Way Lo
 * @date 2019/7/15
 */
class ShareImageDialog(context: AppCompatActivity, data: ShareEntity) : ShareWxQq(context, data) {

    override fun initOtherChannels() {
        (mListChannel as ArrayList<ChannelEntity>).add(ChannelEntity(ShareConstant.SHARE_CHANNEL_SYSTEM,
            R.drawable.share_more, context.getString(R.string.share_channel_save)))
    }

    override fun otherChannelsClick(item: ChannelEntity) {

    }
}