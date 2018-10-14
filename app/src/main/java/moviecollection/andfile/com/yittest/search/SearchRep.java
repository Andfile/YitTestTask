package moviecollection.andfile.com.yittest.search;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moviecollection.andfile.com.yittest.SearchCallbacks;
import moviecollection.andfile.com.yittest.model.HitsItem;
import moviecollection.andfile.com.yittest.model.PixResponse;
import moviecollection.andfile.com.yittest.network.Network;
import moviecollection.andfile.com.yittest.utils.Const;
import moviecollection.andfile.com.yittest.utils.Tools;

/**
 * Created by Andrey Lebedev on 10/13/2018.
 */
public class SearchRep<MODEL> implements SearchCallbacks<MODEL>{
    private int totalRequestHits;
    private int currentPage;
    private String currentQuery;
    private Map<String, List<HitsItem>> allSessionData;

    @Override
    public void search(String query, final SearchCallbacks.ViewModel repoCallback) {
        currentPage = 1;
        currentQuery = query;
        allSessionData = new HashMap<>();
        Log.e("xxx-SearchRepo","search: " + query);
        Map<String, String> params = getParamsSearch(query,currentPage);

        Network.getInstance().search(params, new SearchCallbacks.OnNetwork<PixResponse>() {
            @Override
            public void onDataLoaded(PixResponse data) {
                if(data.getHits() != null){
                    totalRequestHits = data.getTotal();
                    allSessionData.put(String.valueOf(currentPage),data.getHits());
                    repoCallback.onData(data.getHits());
                    return;
                }
                repoCallback.onError();
            }

            @Override
            public void onError() {
                repoCallback.onError();
            }
        });
    }

    @NonNull
    private Map<String, String> getParamsSearch(String query,int page) {
        Map<String, String> params = new HashMap<>();
        params.put("q",query);
        params.put("key", Const.API_KEY_PIX);
        params.put("page",String.valueOf(page));
        return params;
    }

    @Override
    public void getNextPage(final SearchCallbacks.ViewModel repoCallback) {

        if(!Tools.isNextPageExist(currentPage,totalRequestHits)){
            return;
        }
        currentPage++;
        Network.getInstance().search(getParamsSearch(currentQuery, currentPage), new OnNetwork<PixResponse>() {
            @Override
            public void onDataLoaded(@NonNull PixResponse data) {
                if(data.getHits() != null){
                    allSessionData.put(String.valueOf(currentPage),data.getHits());
                    repoCallback.onData(data.getHits());
                    return;
                }
                repoCallback.onError();
            }

            @Override
            public void onError() {
                repoCallback.onError();
            }
        });
    }

    public void getAllData(SearchCallbacks.ViewModel repoCallback){
        if(allSessionData == null){
            return;
        }
        List<HitsItem> data = new ArrayList<>();

        for(List<HitsItem> a: allSessionData.values()){
            data.addAll(a);
        }

        repoCallback.onData(data);
    }

    public void clearRepo(){

        allSessionData = null;
    }
}



