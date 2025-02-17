package com.xyzlf.share.library.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xyzlf.share.library.R;
import com.xyzlf.share.library.ShareHelper;
import com.xyzlf.share.library.adapter.AppGridAdapter;
import com.xyzlf.share.library.bean.ChannelEntity;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.util.ChannelUtil;
import com.xyzlf.share.library.util.ShareConstant;
import com.xyzlf.share.library.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


public class ShareDialogActivity extends ShareBaseActivity implements AdapterView.OnItemClickListener {

    protected List<ChannelEntity> channelEntities = new ArrayList<>();

    protected ShareEntity data;
    protected SparseArray<ShareEntity> sparseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_dialog_link);

        Object object = null;
        if (getIntent().hasExtra(ShareConstant.EXTRA_SHARE_DATA)) {
            Bundle bundle = null;
            try {
                bundle = getIntent().getBundleExtra(ShareConstant.EXTRA_SHARE_DATA);
            } catch (Exception ignore) {
            }

            if (null != bundle) {
                object = bundle.get(ShareConstant.EXTRA_SHARE_DATA);
            } else {
                try {
                    object = getIntent().getParcelableExtra(ShareConstant.EXTRA_SHARE_DATA);
                } catch (Exception ignore) {
                }
                if (null == object) {
                    object = getIntent().getSerializableExtra(ShareConstant.EXTRA_SHARE_DATA);
                }
            }
        } else {
            object = getIntent().getData();
        }

        if (null == object) {
            ToastUtil.showToast(this, getString(R.string.share_empty_tip), true);
            finish();
            return;
        }

        if (object instanceof ShareEntity) {
            data = (ShareEntity) object;
        } else if (object instanceof SparseArray) {
            sparseArray = (SparseArray<ShareEntity>) object;
        }
        if (data == null && sparseArray == null) {
            ToastUtil.showToast(this, getString(R.string.share_empty_tip), true);
            finish();
            return;
        }

        initChannelData();
        if (channelEntities.isEmpty()) {
            finish();
            return;
        }
        initView();
    }

    private void initChannelData() {
        /** weixin **/
        if (ChannelUtil.isWeixinInstall(this)) {
            if ((channel & ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND) > 0 && isShowChannel(ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND)) {
                channelEntities.add(new ChannelEntity(ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND,
                    R.drawable.share_wechat, getString(R.string.share_channel_weixin_friend)));
            }
            if ((channel & ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE) > 0 && isShowChannel(ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE)) {
                channelEntities.add(new ChannelEntity(ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE,
                    R.drawable.share_wxcircle, getString(R.string.share_channel_weixin_circle)));
            }
        }
        /** QQ **/
        if (ChannelUtil.isQQInstall(this)) {
            if ((channel & ShareConstant.SHARE_CHANNEL_QQ) > 0 && isShowChannel(ShareConstant.SHARE_CHANNEL_QQ)) {
                channelEntities.add(new ChannelEntity(ShareConstant.SHARE_CHANNEL_QQ,
                    R.drawable.share_qq, getString(R.string.share_channel_qq)));
            }
            if ((channel & ShareConstant.SHARE_CHANNEL_QZONE) > 0 && isShowChannel(ShareConstant.SHARE_CHANNEL_QZONE)) {
                channelEntities.add(new ChannelEntity(ShareConstant.SHARE_CHANNEL_QZONE,
                    R.drawable.share_qzone, getString(R.string.share_channel_qzone)));
            }
        }
        /** weibo **/
        if ((channel & ShareConstant.SHARE_CHANNEL_SINA_WEIBO) > 0 && isShowChannel(ShareConstant.SHARE_CHANNEL_SINA_WEIBO)) {
            channelEntities.add(new ChannelEntity(ShareConstant.SHARE_CHANNEL_SINA_WEIBO,
                R.drawable.share_weibo, getString(R.string.share_channel_weibo)));
        }
        /** more **/
        if ((channel & ShareConstant.SHARE_CHANNEL_SYSTEM) > 0 && isShowChannel(ShareConstant.SHARE_CHANNEL_SYSTEM)) {
            channelEntities.add(new ChannelEntity(ShareConstant.SHARE_CHANNEL_SYSTEM,
                R.drawable.share_more, getString(R.string.share_channel_more)));
        }
    }

    private boolean isShowChannel(int channel) {
        if (sparseArray != null) {
            return sparseArray.get(channel) != null;
        }
        return true;
    }

    private void initView() {
        AppGridAdapter adapter = new AppGridAdapter(this, channelEntities);
        GridView shareGridView = (GridView) findViewById(R.id.share_grid);
        shareGridView.setAdapter(adapter);
        shareGridView.setOnItemClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ShareConstant.REQUEST_CODE) {
            if (null != data) {
                int channel = data.getIntExtra(ShareConstant.EXTRA_SHARE_CHANNEL, -1);
                int status = data.getIntExtra(ShareConstant.EXTRA_SHARE_STATUS, -1);
                finishWithResult(channel, status);
                return;
            }
        }
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChannelEntity data = ((ChannelEntity) parent.getAdapter().getItem(position));
        if (null == data) {
            return;
        }
        handleShare(data.getChannel());
    }

    /**
     * 分享
     *
     * @param channel {@link ShareConstant#SHARE_CHANNEL_ALL}
     */
    protected void handleShare(int channel) {
        switch (channel) {
            case ShareConstant.SHARE_CHANNEL_QQ:
                shareByQQ();
                break;
            case ShareConstant.SHARE_CHANNEL_QZONE:
                shareByQZone();
                break;

            case ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND:
                shareByWeixinFriend();
                break;
            case ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE:
                shareByWeixinCircle();
                break;

            case ShareConstant.SHARE_CHANNEL_SINA_WEIBO:
                shareBySinaWeibo();
                break;

            case ShareConstant.SHARE_CHANNEL_SYSTEM:
                shareBySystem();
                finish();
                break;

            case ShareConstant.SHARE_CHANNEL_SMS:
                shareBySms();
                break;

            case ShareConstant.SHARE_CHANNEL_EMAIL:
                shareByEmail();
                break;
        }
    }

    /**
     * 分享QQ好友
     */
    protected void shareByQQ() {
        ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_QQ,
            getShareData(ShareConstant.SHARE_CHANNEL_QQ), ShareConstant.REQUEST_CODE);
    }

    /**
     * 分享到QQ空间
     */
    protected void shareByQZone() {
        ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_QZONE,
            getShareData(ShareConstant.SHARE_CHANNEL_QZONE), ShareConstant.REQUEST_CODE);
    }

    /**
     * 分享微信好友
     */
    protected void shareByWeixinFriend() {
        ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND,
            getShareData(ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND), ShareConstant.REQUEST_CODE);
    }

    /**
     * share to weixin circle
     */
    protected void shareByWeixinCircle() {
        ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE,
            getShareData(ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE), ShareConstant.REQUEST_CODE);
    }

    /**
     * share more
     */
    protected void shareBySystem() {
        ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_SYSTEM,
            getShareData(ShareConstant.SHARE_CHANNEL_SYSTEM), ShareConstant.REQUEST_CODE);
    }

    /**
     * share sms
     */
    protected void shareBySms() {
        ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_SMS,
            getShareData(ShareConstant.SHARE_CHANNEL_SMS), ShareConstant.REQUEST_CODE);
    }

    /**
     * share email
     */
    protected void shareByEmail() {
        ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_EMAIL,
            getShareData(ShareConstant.SHARE_CHANNEL_EMAIL), ShareConstant.REQUEST_CODE);
    }

    /**
     * share weibo
     */
    protected void shareBySinaWeibo() {
        ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_SINA_WEIBO,
            getShareData(ShareConstant.SHARE_CHANNEL_SINA_WEIBO), ShareConstant.REQUEST_CODE);
    }

    protected ShareEntity getShareData(int shareChannel) {
        if (data != null) {
            return data;
        }
        if (sparseArray != null) {
            return sparseArray.get(shareChannel);
        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && isOutOfBounds(this, event)) {
            finish();
            return true;
        }
        return super.onTouchEvent(event);
    }

    private boolean isOutOfBounds(Activity context, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = context.getWindow().getDecorView();
        return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop)) || (y > (decorView.getHeight() + slop));
    }


}
