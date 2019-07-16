package com.xyzlf.share.library.interfaces;

/**
 * Fuction:
 *
 * @author Way Lo
 * @date 2019/7/15
 */
public interface OnDownloadListener<T> {

    void onSuccess(T t);

    void onFail(Exception exception);
}
