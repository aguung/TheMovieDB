package com.agungsubastian.themoviedb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agungsubastian.themoviedb.Helper.Utils;
import com.agungsubastian.themoviedb.Model.DataModel;
import com.agungsubastian.themoviedb.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ListViewHolder> {

    private ArrayList<DataModel> listData = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;
    private Context context;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<DataModel> items,Context context) {
        this.context = context;
        listData.clear();
        listData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.bind(listData.get(position));

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtScore;
        private TextView txtDate;
        private TextView txtDescription;
        private ImageView imgData;

        ListViewHolder(@NonNull View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txt_title);
            txtScore = view.findViewById(R.id.txt_score);
            txtDate = view.findViewById(R.id.txt_date);
            txtDescription = view.findViewById(R.id.txt_description);
            imgData = view.findViewById(R.id.img_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(listData.get(getAdapterPosition()));
                }
            });
        }

        void bind(DataModel dataModel){
            txtTitle.setText(dataModel.getTitle());
            txtScore.setText(dataModel.getScore());
            txtDate.setText(dataModel.getDate());
            txtDescription.setText(dataModel.getDescription());
            Glide.with(context)
                    .load(Utils.BASE_URL_IMAGE+"/w92/"+dataModel.getImage())
                    .error(R.drawable.ic_error)
                    .centerInside()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imgData);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(DataModel dataModel);
    }
}
