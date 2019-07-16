package com.xyzlf.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xyzlf.share.library.ShareHelper;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.util.ShareConstant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ShareEntity testBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testBean = new ShareEntity("我是标题", "我是内容，描述内容。");
        testBean.setUrl("https://www.baidu.com"); //分享链接
        testBean.setImgUrl("https://www.baidu.com/img/bd_logo1.png");

        findViewById(R.id.share_qq).setOnClickListener(this);
        findViewById(R.id.share_qzone).setOnClickListener(this);
        findViewById(R.id.share_weibo).setOnClickListener(this);
        findViewById(R.id.share_weixin).setOnClickListener(this);
        findViewById(R.id.share_image).setOnClickListener(this);
        findViewById(R.id.share_link).setOnClickListener(this);
        findViewById(R.id.share_poster).setOnClickListener(this);
        findViewById(R.id.share_bigimage).setOnClickListener(this);

    }

    /**
     * 使用统一数据结构
     */
    public void showShareDialog(View view) {
        ShareEntity testBean = new ShareEntity("我是标题", "我是内容，描述内容。");
        testBean.setUrl("https://www.baidu.com"); //分享链接
        testBean.setImgUrl("https://www.baidu.com/img/bd_logo1.png");
        ShareHelper.INSTANCE.showShareDialog(this, testBean, ShareConstant.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 分享回调处理
         */
        if (requestCode == ShareConstant.REQUEST_CODE) {
            if (data != null) {
                int channel = data.getIntExtra(ShareConstant.EXTRA_SHARE_CHANNEL, -1);
                int status = data.getIntExtra(ShareConstant.EXTRA_SHARE_STATUS, -1);
                onShareCallback(channel, status);
            }
        }
    }

    /**
     * 分享回调处理
     * @param channel
     * @param status
     */
    private void onShareCallback(int channel, int status) {
        new ShareCallBack().onShareCallback(channel, status);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.share_qq:
                ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_QQ, testBean,
                    ShareConstant.REQUEST_CODE);
                break;
            case R.id.share_qzone:
                ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_QZONE, testBean
                    , ShareConstant.REQUEST_CODE);
                break;
            case R.id.share_weibo:
                ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_SINA_WEIBO, testBean,
                    ShareConstant.REQUEST_CODE);
                break;
            case R.id.share_weixin:
                ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND,
                    testBean, ShareConstant.REQUEST_CODE);
                break;
            case R.id.share_wxcircle:
                ShareHelper.INSTANCE.startShare(this, ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE, testBean, ShareConstant.REQUEST_CODE);
                break;
            //分享大图
            case R.id.share_bigimage:
                shareBigImg();
                break;
            case R.id.share_image:
                shareWxQqImage();
                break;
            case R.id.share_link:
                shareWxQqLink();
                break;
            case R.id.share_poster:
                shareWxQqPoster();
                break;
        }
    }

    /**
     * 分享大图，大图分享支持，微信，微信朋友圈，微博，QQ，其他渠道不支持
     *
     * 分享大图注意点
     * 1、setShareBigImg为ture
     * 2、QQ分享大图，只能是本地图片
     */
    public void shareBigImg() {
        ShareEntity testBean = new ShareEntity("", "");
        testBean.setShareBigImg(true);
//        testBean.setImgUrl("https://www.baidu.com/img/bd_logo1.png"); // 网络地址
        testBean.setImgUrl("/storage/sdcard0/Android/data/com.xyzlf.share/files/com.xyzlf.share_share_pic.png"); //本地地址

        /** 如果你要分享的图片是Bitmap，你可以如下使用 **/
//        Bitmap bitmap = null;
//        String filePath = ShareUtil.saveBitmapToSDCard(this, bitmap);
//        testBean.setImgUrl(filePath);

        int channel =
            ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND | ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE
            | ShareConstant.SHARE_CHANNEL_SINA_WEIBO | ShareConstant.SHARE_CHANNEL_QQ;
        ShareHelper.INSTANCE.showShareDialog(this, channel, testBean, ShareConstant.REQUEST_CODE);
    }

    public void shareWxQqImage() {
        ShareEntity testBean = new ShareEntity("标题", "分享正文");
        testBean.setShareBigImg(true);
        List<String> list = new ArrayList<>();
        list.add("https://www.baidu.com/img/bd_logo1.png");
        testBean.setImgUrls(list);
        testBean.setImgUrl("https://www.baidu.com/img/bd_logo1.png");
        ShareHelper.INSTANCE.showShareWxQqImageDialog(this, testBean);
    }

    public void shareWxQqLink() {
        ShareEntity testBean = new ShareEntity("标题", "分享正文");
        testBean.setShareBigImg(true);
        List<String> list = new ArrayList<>();
        list.add("https://www.baidu.com/img/bd_logo1.png");
        testBean.setImgUrls(list);
        testBean.setImgUrl("https://www.baidu.com/img/bd_logo1.png");
        ShareHelper.INSTANCE.showShareWxQqLinkDialog(this, testBean);
    }

    public void shareWxQqPoster() {
        ShareEntity testBean = new ShareEntity("标题", "分享正文");
        testBean.setShareBigImg(true);
        List<String> list = new ArrayList<>();
        list.add("https://www.baidu.com/img/bd_logo1.png");
        testBean.setImgUrls(list);
        testBean.setImgUrl("https://www.baidu.com/img/bd_logo1.png");
        ShareHelper.INSTANCE.showShareWxQqPosterDialog(this, testBean);
    }
}
