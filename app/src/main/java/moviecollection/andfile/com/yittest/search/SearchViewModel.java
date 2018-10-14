package moviecollection.andfile.com.yittest.search;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moviecollection.andfile.com.yittest.SearchCallbacks;
import moviecollection.andfile.com.yittest.model.HitsItem;

/**
 * Created by Andrey Lebedev on 10/13/2018.
 */
public class SearchViewModel<MODEL> extends ViewModel implements LifecycleObserver {

    private MutableLiveData<MODEL> searchedData;
    private SearchRep<MODEL> repository;
    public LiveData<MODEL> getData() {

        if (searchedData == null) {
            searchedData = new MutableLiveData<>();
        }
        if (repository == null) {
            repository = new SearchRep<>();
        }

        return searchedData;
    }

    public void search(@NonNull String toSearch) {
        if (TextUtils.isEmpty(toSearch)) {
            return;
        }

        Log.e("xxx-SearchVM", "search: " + toSearch);

        repository.search(toSearch, new SearchCallbacks.ViewModel<MODEL>() {
            @Override
            public void onData(@NonNull MODEL data) {
                Log.e("xxx-SearchVM", "onDataLoaded");
                searchedData.setValue(data);
            }

            @Override
            public void onError() {
                //implement error
            }
        });
    }

    public void getNextPage() {

        repository.getNextPage(new SearchCallbacks.ViewModel<MODEL>() {
            @Override
            public void onData(@NonNull MODEL data) {

                searchedData.setValue(data);
            }

            @Override
            public void onError() {
                //implement error
            }
        });
    }

    public void clearSearch(){


    }

    @Override
    protected void onCleared() {
        repository.clearRepo();
        Log.e("xxx-viewModel","onCleared");
        super.onCleared();
    }

    public void setAllData() {

        Log.e("xxx-onDestroy","ondestroy");
        searchedData = new MutableLiveData<>();
        repository.getAllData(new SearchCallbacks.ViewModel<MODEL>() {
            @Override
            public void onData(@NonNull MODEL data) {
                searchedData.setValue(data);
            }

            @Override
            public void onError() {

            }
        });
    }
}
