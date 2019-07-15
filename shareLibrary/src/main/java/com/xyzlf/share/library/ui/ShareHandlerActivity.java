package com.xyzlf.share.library.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.channel.ShareByEmail;
import com.xyzlf.share.library.channel.ShareByQQ;
import com.xyzlf.share.library.channel.ShareByQZone;
import com.xyzlf.share.library.channel.ShareBySms;
import com.xyzlf.share.library.channel.ShareBySystem;
import com.xyzlf.share.library.channel.ShareByWeibo;
import com.xyzlf.share.library.channel.ShareByWeixin;
import com.xyzlf.share.library.interfaces.OnShareListener;
import com.xyzlf.share.library.interfaces.ShareConstant;

/**
 * Created by zhanglifeng on 16/06/20
 *
 * 分发Activity，主要对分享功能进行分发
 */
public class ShareHandlerActivity extends ShareBaseActivity implements OnShareListener {

    protected ShareEntity data;

    protected ShareByWeixin shareByWeixin;
    protected boolean isInit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Object object = null;
        try {
            //Fuzz问题处理
            object = getIntent().getParcelableExtra(ShareConstant.EXTRA_SHARE_DATA);
        } catch (Exception e) {}
        if (!(object instanceof ShareEntity)) {
            finish();
            return;
        }
        data = (ShareEntity) object;

        if (savedInstanceState == null) {
            if (null != shareByWeixin) {
                shareByWeixin.unregisterWeixinReceiver();
                shareByWeixin = null;
            }
            switch (channel) {
                case ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE:
                    shareByWeixin = new ShareByWeixin(this, ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE);
                    shareByWeixin.registerWeixinReceiver();
                    shareByWeixin.share(data, this);
                    break;

                case ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND:
                    shareByWeixin = new ShareByWeixin(this, ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND);
                    shareByWeixin.registerWeixinReceiver();
                    shareByWeixin.share(data, this);
                    break;

                case ShareConstant.SHARE_CHANNEL_SINA_WEIBO:
                    new ShareByWeibo(this).share(data, this);
                    break;

                case ShareConstant.SHARE_CHANNEL_QQ:
                    new ShareByQQ(this).share(data, this);
                    break;

                case ShareConstant.SHARE_CHANNEL_QZONE:
                    new ShareByQZone(this).share(data, this);
                    break;

                case ShareConstant.SHARE_CHANNEL_SMS:
                    new ShareBySms(this).share(data, this);
                    break;

                case ShareConstant.SHARE_CHANNEL_EMAIL:
                    new ShareByEmail(this).share(data, this);
                    break;

                case ShareConstant.SHARE_CHANNEL_SYSTEM:
                    new ShareBySystem(this).share(data, this);
                    break;

                default:
                    finishWithResult(channel, ShareConstant.SHARE_STATUS_ERROR);
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInit) {
            finishWithResult(channel, ShareConstant.SHARE_STATUS_ERROR);
        } else {
            isInit = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_QQ_SHARE || requestCode == Constants.REQUEST_QZONE_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, null);
        }
    }

    @Override
    public void onShare(int channel, int status) {
        finishWithResult(channel, status);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != shareByWeixin) {
            shareByWeixin.unregisterWeixinReceiver();
        }
    }
}
