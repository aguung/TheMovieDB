package com.agungsubastian.themoviedb.Helper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {


    @GET("{type}?")
    Call<ResponseBody> dataRequest(@Path("type") String type,@Query("api_key") String api, @Query("language") String language);

}