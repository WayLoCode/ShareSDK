package com.xyzlf.share.library.util

import android.util.Log
import com.xyzlf.share.library.BuildConfig

/**
 * Fuction:
 * @author  Way Lo
 * @date 2019/7/15
 */
object ShareLogUtil {

    private const val TAG = "ShareLogUtil"
    private var DEBUG = BuildConfig.DEBUG

    fun d(msg: String) {
        if (DEBUG) {
            Log.d(TAG, msg)
        }
    }

    fun e(msg: String) {
        if (DEBUG) {
            Log.e(TAG, msg)
        }
    }

    fun e(e: Throwable) {
        if (DEBUG) {
            Log.e(TAG, e.message)
        }
    }
}