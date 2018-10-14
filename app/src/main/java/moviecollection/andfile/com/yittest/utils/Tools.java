package moviecollection.andfile.com.yittest.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import moviecollection.andfile.com.yittest.R;

public class Tools {

    public static void glideImageDownlad (String imageURL, ImageView imageView){

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_loading)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.ic_loading);

        Glide.with(imageView)
                .load(imageURL)
                .apply(options)
                .into(imageView);

    }

    public static boolean isNextPageExist(int currentPage, int totalHits){
            return (totalHits - (currentPage * Const.PAGE_SIZE)) > 0;
    }
}
