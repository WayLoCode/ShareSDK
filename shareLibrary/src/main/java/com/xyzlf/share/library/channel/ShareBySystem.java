package com.xyzlf.share.library.channel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.xyzlf.share.library.R;
import com.xyzlf.share.library.ShareHelper;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.OnShareListener;
import com.xyzlf.share.library.util.ShareConstant;
import com.xyzlf.share.library.util.ToastUtil;

/**
 * Created by zhanglifeng
 */
public class ShareBySystem extends ShareBase {

    public ShareBySystem(AppCompatActivity context) {
        super(context);
    }

    @Override
    public void share(ShareEntity data, OnShareListener listener) {
        if (data == null || TextUtils.isEmpty(data.getContent())) {
            ToastUtil.showToast(mContext, R.string.share_empty_tip, true);
            return;
        }
        String content;
        if (TextUtils.isEmpty(data.getContent())) {
            content = data.getTitle() + data.getUrl();
        } else {
            content = data.getContent() + data.getUrl();
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.setType("text/plain");
        if (ShareHelper.INSTANCE.startThirdActivity(mContext, Intent.createChooser(
            shareIntent, mContext.getString(R.string.share_to)))) {
            if (null != listener) {
                listener.onShare(ShareConstant.SHARE_CHANNEL_SYSTEM, ShareConstant.SHARE_STATUS_COMPLETE);
            }
        } else {
            if (null != listener) {
                listener.onShare(ShareConstant.SHARE_CHANNEL_SYSTEM, ShareConstant.SHARE_STATUS_FAILED);
            }
        }
    }
}
