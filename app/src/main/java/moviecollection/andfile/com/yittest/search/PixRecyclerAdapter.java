package moviecollection.andfile.com.yittest.search;

import android.app.ActionBar;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import moviecollection.andfile.com.yittest.MainActivity;
import moviecollection.andfile.com.yittest.R;
import moviecollection.andfile.com.yittest.model.HitsItem;
import moviecollection.andfile.com.yittest.utils.Const;
import moviecollection.andfile.com.yittest.utils.ItemHeightCalculateHolder;
import moviecollection.andfile.com.yittest.utils.Tools;

public class PixRecyclerAdapter extends RecyclerView.Adapter<PixRecyclerViewHolder> {

    private List<HitsItem> hitsData = new ArrayList<>();
    private ConstraintLayout itemContainer;
    private RecyclerLoadNextPage loadNextPageCallback;
    private boolean isPageLoadingInProgress = false;
    private int adapterPosition;

    public PixRecyclerAdapter(RecyclerLoadNextPage callback) {

        loadNextPageCallback = callback;
    }

    @NonNull
    @Override
    public PixRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        itemContainer = view.findViewById(R.id.item_container);

        FlexboxLayoutManager.LayoutParams params = (FlexboxLayoutManager.LayoutParams) itemContainer.getLayoutParams();
        params.height = ItemHeightCalculateHolder.getInstance().getStoredItemHeight();
        params.width = ActionBar.LayoutParams.WRAP_CONTENT;

        itemContainer.setLayoutParams(params);
        PixRecyclerViewHolder vh = new PixRecyclerViewHolder(view);
        return vh;
    }

    public void addData(List<HitsItem> data) {

        hitsData.addAll(data);
        isPageLoadingInProgress = false;
        notifyDataSetChanged();
    }

    public void clearData(){

        hitsData = new ArrayList<>();
        isPageLoadingInProgress = false;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PixRecyclerViewHolder pixRecyclerViewHolder, int position) {
        Tools.glideImageDownlad(hitsData.get(position).getPreviewURL(),
                        pixRecyclerViewHolder.searchResultImage);
        adapterPosition = position;
        loadNextPageIfNeed(position);

    }

    private void loadNextPageIfNeed(int position) {
        if ((position > (hitsData.size() - Const.PAGE_TIGER_TO_DOWNLOAD)) && !isPageLoadingInProgress) {
            isPageLoadingInProgress = true;
            loadNextPageCallback.loadNextpage();
        }
    }

    @Override
    public int getItemCount() {
        return hitsData.size();
    }

    public int getPosition(){

        return adapterPosition;
    }
}
