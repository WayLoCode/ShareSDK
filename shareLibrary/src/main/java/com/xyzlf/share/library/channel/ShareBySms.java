package com.xyzlf.share.library.channel;

import android.content.Intent;
import android.net.Uri;
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
public class ShareBySms extends ShareBase {

    public ShareBySms(AppCompatActivity context) {
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

        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "");
        //短信内容
        sendIntent.putExtra("sms_body", content);
        sendIntent.setType("vnd.android-dir/mms-sms");
        if (ShareHelper.INSTANCE.startThirdActivity(mContext, sendIntent)) {
            if (null != listener) {
                listener.onShare(ShareConstant.SHARE_CHANNEL_SMS, ShareConstant.SHARE_STATUS_COMPLETE);
            }
        } else {
            if (null != listener) {
                listener.onShare(ShareConstant.SHARE_CHANNEL_SMS, ShareConstant.SHARE_STATUS_FAILED);
            }
        }
    }
}
