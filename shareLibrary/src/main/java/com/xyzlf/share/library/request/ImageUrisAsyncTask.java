package com.xyzlf.share.library.request;

import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;

import com.xyzlf.share.library.interfaces.OnDownloadListener;
import com.xyzlf.share.library.util.ShareLogUtil;
import com.xyzlf.share.library.util.ShareUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Fuction:
 *
 * @author Way Lo
 * @date 2019/7/15
 */
public class ImageUrisAsyncTask extends AbstractAsyncTask<ArrayList<Uri>> {

    private List<String> mUrls;

    public ImageUrisAsyncTask(List<String> urls, OnDownloadListener<ArrayList<Uri>> listener) {
        this.mUrls = urls;
        this.listener = listener;
    }

    @Override
    protected ArrayList<Uri> doLoadData() {
        ArrayList<Uri> imageUris = new ArrayList<>();
        File file;
        for (String s : mUrls) {
            file = ShareUtil.INSTANCE.saveImageToSdCard(s);
            if (file == null) {
                continue;
            }
            ShareLogUtil.INSTANCE.d("保存图片=" + s);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
            }
            imageUris.add(Uri.fromFile(file));
        }

        return imageUris;
    }
}
