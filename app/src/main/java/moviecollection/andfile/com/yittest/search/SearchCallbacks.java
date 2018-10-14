package moviecollection.andfile.com.yittest.search;

import android.support.annotation.NonNull;

/**
 * Created by Andrey Lebedev on 10/13/2018.
 */
public interface SearchCallbacks<MV> {

    interface OnNetwork<M> {
        void onDataLoaded(@NonNull M data);

        void onError();
    }

    //check if i need it
    void search(String query, ViewModel<MV> callback);

    void getNextPage(ViewModel<MV> callback);


    interface ViewModel<M> {
        void onData(@NonNull M data);

        void onError();
    }
}
