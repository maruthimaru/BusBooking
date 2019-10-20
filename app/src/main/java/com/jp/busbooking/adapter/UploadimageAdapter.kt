package com.jp.busbooking.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

import com.jackandphantom.circularimageview.CircleImage
import com.jp.busbooking.R
import com.jp.busbooking.helper.CommonClass
import com.jp.busbooking.helper.Zoomimage
import com.jp.busbooking.pojo.ImagesModel

class UploadimageAdapter(private val list: List<ImagesModel>, private val context: Context) : RecyclerView.Adapter<UploadimageAdapter.MyViewHolder>() {
    private val dialog: Dialog? = null
    internal var TAG = "UploadimageAdapter"
    private lateinit var commonClass: CommonClass

    init {
        commonClass= CommonClass(context)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        Log.d(TAG, "onCreateViewHolder called")
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.demo_image, null)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e(TAG, "onBindViewHolder: $position")
        val images = list[position]
//        val decodedString = Base64.decode(images.imageList, Base64.DEFAULT)
//        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        commonClass.imageLoad(holder.img, images.imageList)
        holder.img.setOnClickListener { this@UploadimageAdapter.fullscrndialog(images.imageList!!) }
    }

    override fun getItemCount(): Int {
        Log.e(TAG, "getItemCount: " + list.size)
        return list.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: CircleImage

        init {
            this.img = view.findViewById(R.id.imageViewDocument)
        }
    }

    init {
        Log.e(TAG, "UploadimageAdapter: Image List Size : " + this.list.size)
    }


    private fun fullscrndialog(bitmap: Bitmap) {
        val dialog = Dialog(this.context)
        dialog.requestWindowFeature(1)
        dialog.setContentView(R.layout.fullprofimage_demo)
        dialog.window!!.setLayout(-1, -1)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        val cancel = dialog.findViewById<ImageView>(R.id.cancel_button)
        (dialog.findViewById<View>(R.id.fullimageView) as Zoomimage).setImageBitmap(bitmap)
        cancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}
