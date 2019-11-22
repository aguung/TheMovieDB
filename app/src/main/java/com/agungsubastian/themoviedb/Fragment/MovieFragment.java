package com.agungsubastian.themoviedb.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.agungsubastian.themoviedb.Adapter.DataAdapter;
import com.agungsubastian.themoviedb.Activity.DetailDataActivity;
import com.agungsubastian.themoviedb.Model.DataModel;
import com.agungsubastian.themoviedb.R;

import java.util.ArrayList;
import java.util.Locale;

public class MovieFragment extends Fragment {

    private DataAdapter adapter;
    private ProgressBar progressBar;

    public MovieFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        RecyclerView rv_movie = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progressBar);

        rv_movie.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DataAdapter();
        adapter.notifyDataSetChanged();
        rv_movie.setAdapter(adapter);


        MovieViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MovieViewModel.class);

        if(Locale.getDefault().getDisplayLanguage().equals("Indonesia")){
            mainViewModel.setData(getContext(),"id-ID");
        }else{
            mainViewModel.setData(getContext(),"en-US");
        }
        showLoading(true);

        mainViewModel.getData().observe(this, new Observer<ArrayList<DataModel>>() {
            @Override
            public void onChanged(ArrayList<DataModel> Items) {
                if (Items != null) {
                    adapter.setData(Items,getContext());
                    showLoading(false);
                }
            }
        });
        adapter.setOnItemClickCallback(new DataAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DataModel dataModel) {
                Intent movieIntent = new Intent(getContext(), DetailDataActivity.class);
                movieIntent.putExtra(DetailDataActivity.EXTRA_MOVIE, dataModel);
                startActivity(movieIntent);
            }
        });
        return view;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
