package cn.edu.cqu.caijimovie.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override

    public void displayImage(Context context, Object resource, ImageView imageView) {


        Glide.with(context).load(resource)
                .centerCrop()
                .into(imageView);


    }


}