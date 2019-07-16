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
public class ShareByEmail extends ShareBase {

    public ShareByEmail(AppCompatActivity context) {
        super(context);
    }

    @Override
    public void share(ShareEntity data, OnShareListener listener) {
        if (data == null || TextUtils.isEmpty(data.getContent())) {
            ToastUtil.showToast(mContext, R.string.share_empty_tip, true);
            return;
        }
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        //邮件主题
        if (!TextUtils.isEmpty(data.getTitle())) {
            email.putExtra(Intent.EXTRA_SUBJECT, data.getTitle());
        }
        //邮件内容
        String contentt = data.getContent() + data.getUrl();
        email.putExtra(Intent.EXTRA_TEXT, contentt);
        if (ShareHelper.INSTANCE.startThirdActivity(mContext, email)) {
            if (null != listener) {
                listener.onShare(ShareConstant.SHARE_CHANNEL_EMAIL, ShareConstant.SHARE_STATUS_COMPLETE);
            }
        } else {
            if (null != listener) {
                listener.onShare(ShareConstant.SHARE_CHANNEL_EMAIL, ShareConstant.SHARE_STATUS_FAILED);
            }
        }
    }
}
