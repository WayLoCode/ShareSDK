package com.xyzlf.share.library.channel;

import android.support.v7.app.AppCompatActivity;

import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.IShareBase;


/**
 * Created by zhanglifeng
 */
public abstract class ShareBase implements IShareBase {

    protected AppCompatActivity mContext;
    protected ShareEntity mData;

    public ShareBase(AppCompatActivity context) {
        this.mContext = context;
    }

    public ShareBase(AppCompatActivity context, ShareEntity data) {
        this(context);
        this.mData = data;
    }
}
