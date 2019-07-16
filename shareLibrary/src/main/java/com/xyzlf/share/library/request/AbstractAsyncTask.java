package com.xyzlf.share.library.request;

import android.os.AsyncTask;

import com.xyzlf.share.library.interfaces.OnDownloadListener;

/**
 * Created by zhanglifeng on 16/6/17
 */
public abstract class AbstractAsyncTask<T> extends AsyncTask<Void, Integer, T> {
    private Exception exception;
    private T data;
    protected OnDownloadListener<T> listener;

    @Override
    protected T doInBackground(Void... voids) {
        try {
            data = doLoadData();
            exception = null;
        } catch (Exception e) {
            data = null;
            exception = e;
        }
        return getData();
    }

    protected abstract T doLoadData() throws Exception;

    public Exception getException() {
        return exception;
    }

    public T getData() {
        return data;
    }

    @Override
    protected void onPostExecute(T t) {
        try {
            if (exception == null) {
                onSuccess(t);
            } else {
                onFail(exception);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            onFinally();
        }

    }

    public void onSuccess(T t) {
        if (null != listener) {
            listener.onSuccess(t);
        }
    }

    public void onFail(Exception exception) {
        if (null != listener) {
            listener.onFail(exception);
        }
    }

    public void onFinally() {
    }

}
