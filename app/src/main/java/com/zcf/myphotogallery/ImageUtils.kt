package com.zcf.myphotogallery

import android.graphics.BitmapFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File


private fun setImageViewFromPath(imgView: ImageView, photoPath: String) {
    val targetWidth = imgView.width
    val targetHeight = imgView.height
    val bmpOptions = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
        BitmapFactory.decodeFile(photoPath, this)
        val photoWidth = outWidth
        val photoHeight = outHeight
        val scaleFactor = Math.min(photoWidth/targetWidth, photoHeight/targetHeight)

        inJustDecodeBounds = false
        inSampleSize = scaleFactor
    }
    BitmapFactory.decodeFile(photoPath, bmpOptions)?.apply {
        imgView.setImageBitmap(this)
    }
}

fun ImageView.setImagePath(imagePath: String) {
    Glide.with(this).load(File(imagePath)).centerCrop().into(this)
}
