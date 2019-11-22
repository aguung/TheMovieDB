package com.agungsubastian.themoviedb.Fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.agungsubastian.themoviedb.Helper.BaseApiService;
import com.agungsubastian.themoviedb.Helper.RetrofitClient;
import com.agungsubastian.themoviedb.Helper.Utils;
import com.agungsubastian.themoviedb.Model.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TVViewModel extends ViewModel {
    private MutableLiveData<ArrayList<DataModel>> listData = new MutableLiveData<>();

    void setData(final Context context,String language) {
        Retrofit retrofit = RetrofitClient.initializeRetrofit();
        BaseApiService service = retrofit.create(BaseApiService.class);
        Call<ResponseBody> result = service.dataRequest("tv",Utils.API_KEY,language);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.body() != null){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        JSONArray array = object.getJSONArray("results");
                        ArrayList<DataModel> listItems = new ArrayList<>();
                        for(int x = 0 ; x < array.length() ; x++ ){
                            JSONObject obj = array.getJSONObject(x);
                            DataModel dataModel = new DataModel();
                            dataModel.setTitle(obj.optString("original_name"));
                            dataModel.setScore(obj.optString("vote_average"));
                            dataModel.setDate(obj.optString("first_air_date"));
                            dataModel.setDescription(obj.optString("overview"));
                            dataModel.setImage(obj.optString("poster_path"));
                            listItems.add(dataModel);
                        }
                        listData.postValue(listItems);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Utils.resultConnection(t,context);
            }
        });
    }

    LiveData<ArrayList<DataModel>> getData() {
        return listData;
    }
}
