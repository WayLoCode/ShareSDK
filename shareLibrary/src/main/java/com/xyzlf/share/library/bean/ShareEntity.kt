package com.xyzlf.share.library.bean

import java.io.Serializable

/**
 * Created by zhanglifeng
 */
class ShareEntity : Serializable {

    var title: String? = null
    var content: String? = null
    var url: String? = null

    var imgUrl: String? = null
    var drawableId: Int = 0
    var isShareBigImg: Boolean = false
    var imgUrls: List<String>? = null

    @JvmOverloads
    constructor(title: String, content: String, url: String? = null, imgUrl: String? = null) {
        this.title = title
        this.content = content
        this.url = url
        this.imgUrl = imgUrl
    }

}
