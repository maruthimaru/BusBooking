package com.jp.busbooking.pojo

import android.graphics.Bitmap
import java.io.Serializable

class ImagesModel : Serializable {
    var imageList: Bitmap? = null

    constructor(imageList: Bitmap?) {
        this.imageList = imageList
    }
}
