package moviecollection.andfile.com.yittest.network;

import java.util.Map;

import moviecollection.andfile.com.yittest.model.PixResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Andrey Lebedev on 10/13/2018.
 */
public interface INetwork {

    /**
     * This method call to api pixabay.com
     * can 2 parameter is required
     * q - query
     * key = api key
     * there are a lot of optional parameters which u can see in
     * https://pixabay.com/api/docs/
     */
    @GET("api/?")
    Call<PixResponse> search(@QueryMap Map<String, String> params);
}
