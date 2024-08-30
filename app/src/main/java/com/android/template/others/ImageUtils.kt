package com.android.template.others

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.template.R
import com.bumptech.glide.Glide
import com.android.template.base.App
import com.squareup.picasso.Picasso


object ImageUtils {

    fun loadImage(image: ImageView, url: String?) {
        val circularProgressDrawable = CircularProgressDrawable(App.get())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(R.color.progress_tint)
        circularProgressDrawable.start()

        Glide.with(App.get())
            .load(Cons.BASE_URL + "images/" + url)
            .centerCrop()
            .placeholder(circularProgressDrawable)
            .error(R.drawable.error_image)
            .into(image);
    }

    fun loadDrawableImage(ivImage: ImageView, @DrawableRes userImage: Int) {
        Glide.with(App.get())
            .load(userImage)
            .centerCrop()
            .into(ivImage);

    }

    fun loadPicassoImage(ivImage: ImageView, userImage:  String?) {
        Picasso.get()
            .load(Cons.BASE_URL + userImage)
            .centerCrop()
            .fit()
            .noFade()
            .error(R.drawable.ic_broken_image)
            .into(ivImage)
    }

}