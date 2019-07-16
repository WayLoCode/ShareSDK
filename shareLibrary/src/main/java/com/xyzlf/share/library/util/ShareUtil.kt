package com.xyzlf.share.library.util

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import com.xyzlf.share.library.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Fuction:
 * @author  Way Lo
 * @date 2019/7/15
 */
object ShareUtil {

    private const val IMAGE_NAME = "share_"

    /**
     * 调用系统分享图片
     */
    fun share(context: Context, imageUris: ArrayList<Uri>, pkg: String, cls: String) {
        val intent = Intent()
        val comp = ComponentName(pkg, cls)
        intent.component = comp
        intent.action = Intent.ACTION_SEND_MULTIPLE
        intent.type = "image/*"
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(context, intent)
    }

    fun startActivity(context: Context, intent: Intent): Boolean {
        var bResult = true
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            bResult = false
            ShareLogUtil.e(e)
        } catch (e: Exception) {
            bResult = false
            ShareLogUtil.e(e)
        }

        return bResult
    }

    /**
     * 获取指定包名内部版本号
     */
    fun getAppVersionCode(context: Context, pkg: String): Int {
        return try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(pkg, 0)
            info.versionCode
        } catch (e: Exception) {
            ShareLogUtil.e(e.toString())
            0
        }
    }

    //根据网络图片url路径保存到本地相册
    fun saveImageToSdCard(imageUrl: String): File? {
        var success = false
        var file: File? = null
        val bitmap: Bitmap?
        val conn: HttpURLConnection?
        val `is`: InputStream?
        var outStream: FileOutputStream? = null
        try {
            file = createStableImageFile(imageUrl)
            if (file.exists()) {
                return file
            }
            val url = URL(imageUrl)
            conn = url.openConnection() as HttpURLConnection
            `is` = conn.inputStream
            bitmap = BitmapFactory.decodeStream(`is`)

            outStream = FileOutputStream(file.absolutePath)
            bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            success = true

        } catch (e: Exception) {
            ShareLogUtil.e(e)
        } finally {
            try {
                if (outStream != null) {
                    outStream.flush()
                    outStream.close()
                }
            } catch (e: IOException) {
                ShareLogUtil.e(e)
            }
        }

        return if (success) {
            file
        } else {
            null
        }
    }

    //创建本地保存相册路径
    private fun createStableImageFile(imageUrl: String): File {
        val imageFileName = IMAGE_NAME + System.currentTimeMillis() + ".jpg"
        val galleryPath = Environment.getExternalStorageDirectory().toString() + File.separator +
            Environment.DIRECTORY_DCIM + File.separator + "Camera"
        val file = File(galleryPath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return File(galleryPath + File.separator, imageFileName)
    }

    /**
     * save the Bitmap to SDCard
     *
     * @param context context
     * @param bitmap  bitmap
     * @return filePath
     */
    fun saveBitmapToSDCard(context: Context?, bitmap: Bitmap?): String? {
        if (null == context) {
            return null
        }
        if (null == bitmap) {
            ToastUtil.showToast(context, R.string.share_save_bitmap_failed, true)
            return null
        }
        //SDCard is valid
        if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
            ToastUtil.showToast(context, R.string.share_save_bitmap_no_sdcard, true)
            return null
        }
        var filePath: String? = null
        val externalFilesDir = context.getExternalFilesDir(null)
        var dir: String? = null
        if (null != externalFilesDir) {
            dir = externalFilesDir.absolutePath
        }
        val packageName = context.packageName
        if (!TextUtils.isEmpty(dir)) {
            filePath = if (!dir!!.endsWith(File.separator)) {
                dir + File.separator + packageName + "_share_pic.png"
            } else {
                dir + packageName + "_share_pic.png"
            }
            try {
                val file = File(filePath)
                if (file.exists()) {
                    file.delete()
                }
                file.createNewFile()

                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
            } catch (e: Exception) {
                ToastUtil.showToast(context, e.message, true)
            }

        }
        return filePath
    }
}