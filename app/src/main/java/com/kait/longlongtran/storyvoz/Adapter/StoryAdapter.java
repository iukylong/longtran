package com.kait.longlongtran.storyvoz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kait.longlongtran.storyvoz.Model.StoryModel;
import com.kait.longlongtran.storyvoz.R;
import com.kait.longlongtran.storyvoz.Singlaton.ResultIPC;
import com.kait.longlongtran.storyvoz.UI.DetailActivityOffline;

import java.util.ArrayList;

/**
 * Created by LongLongTran on 1/27/2018.
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {

    private ArrayList<StoryModel> mArrayList;
    private Context mContext;
    private ArrayList<StoryModel> modelList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, mContext, mArrayList);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StoryModel model = mArrayList.get(position);
        holder.TenTruyen.setText(model.getmTenTruyen());
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
                "Raleway-Light.ttf");
        holder.TenTruyen.setTypeface(tf);
        holder.TacGia.setText(model.getmTacGia());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public StoryAdapter(ArrayList<StoryModel> mArrayList, Context mContext) {
        this.mArrayList = mArrayList;
        this.mContext = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView TenTruyen, TacGia;
        ArrayList<StoryModel> models = new ArrayList<>();
        Context context;

        public MyViewHolder(View itemView, Context context, ArrayList<StoryModel> models) {
            super(itemView);
            this.models = models;
            this.context = context;
            itemView.setOnClickListener(this);
            TenTruyen = itemView.findViewById(R.id.tv_TenTruyen);
            TacGia = itemView.findViewById(R.id.tv_TacGia);
        }

        @Override
        public void onClick(View view) {
            StoryModel model = this.models.get(getAdapterPosition());
            Intent intent = new Intent(context, DetailActivityOffline.class);
            int sync = ResultIPC.get().setLargeData(model);
            intent.putExtra("sync", sync);
            this.context.startActivity(intent);

        }
    }

    public void setFilter(ArrayList<StoryModel> storyModels) {
        modelList = new ArrayList<>();
        modelList.addAll(storyModels);
        notifyDataSetChanged();
    }
}
