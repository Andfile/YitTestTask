package moviecollection.andfile.com.yittest.network;

import android.util.Log;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import moviecollection.andfile.com.yittest.SearchCallbacks;
import moviecollection.andfile.com.yittest.model.PixResponse;
import moviecollection.andfile.com.yittest.utils.Const;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Andrey Lebedev on 10/13/2018.
 */
public class Network {


    private static Network sInstance;
    private static Retrofit sRetrofit;
    private static INetwork sService;

    private Network() {


        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        sRetrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL_PIX)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sService = sRetrofit.create(INetwork.class);
    }

    public static Network getInstance() {
        if (sInstance == null) {

            sInstance = new Network();
        }
        return sInstance;
    }

    public void search(Map<String, String> params, final SearchCallbacks.OnNetwork repoCallback) {
        sService.search(params).enqueue(new Callback<PixResponse>() {
            @Override
            public void onResponse(Call<PixResponse> call, Response<PixResponse> response) {

                Log.e("xxx-network", "onResponse URL: " + call.request().url());

                if (response.isSuccessful() && response.body() != null) {
                    repoCallback.onDataLoaded(response.body());
                }

            }

            @Override
            public void onFailure(Call<PixResponse> call, Throwable t) {
                Log.e("xxx-network", "onFailure URL: " + call.request().url());
                t.printStackTrace();
                repoCallback.onError();
            }
        });
    }

}
