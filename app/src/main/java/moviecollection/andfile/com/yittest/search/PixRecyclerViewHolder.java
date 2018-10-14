package moviecollection.andfile.com.yittest.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import moviecollection.andfile.com.yittest.R;

public class PixRecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView searchResultImage;

    public PixRecyclerViewHolder(View itemView) {
        super(itemView);
        searchResultImage = itemView.findViewById(R.id.picture_preview);
    }
}
