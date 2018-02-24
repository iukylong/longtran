package com.kait.longlongtran.storyvoz.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kait.longlongtran.storyvoz.Model.StoryModelOnline;
import com.kait.longlongtran.storyvoz.R;

import java.util.ArrayList;

/**
 * Created by LongLongTran on 1/27/2018.
 */

public class StoryAdapterOnline extends RecyclerView.Adapter<StoryAdapterOnline.MyViewHolder> {

    private ArrayList<StoryModelOnline> mArrayList;
    private Context mContext;
    private ArrayList<StoryModelOnline> modelList;
    private OnItemClickListener mListener;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_online, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, mContext, mArrayList);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StoryModelOnline model = mArrayList.get(position);
        holder.TenTruyen.setText(model.getmTenTruyen());
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(),
                "Raleway-Light.ttf");
        holder.TenTruyen.setTypeface(tf);
        holder.TacGia.setText(model.getmTacGia());
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public StoryAdapterOnline(ArrayList<StoryModelOnline> mArrayList, Context mContext) {
        this.mArrayList = mArrayList;
        this.mContext = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TenTruyen, TacGia;
        ArrayList<StoryModelOnline> models = new ArrayList<>();
        Context context;

        public MyViewHolder(View itemView, final Context context, ArrayList<StoryModelOnline> models) {
            super(itemView);
            this.models = models;
            this.context = context;
            TenTruyen = itemView.findViewById(R.id.tv_Title_Online);
            TacGia = itemView.findViewById(R.id.tv_TacGia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int positon = getAdapterPosition();
                        if (positon != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(positon);
                        }
                    }
                }
            });
        }

    }

    /**
     * set fillter for Search Recyclerview
     *
     * @param storyModels
     */
    public void setFilter(ArrayList<StoryModelOnline> storyModels) {
        modelList = new ArrayList<>();
        modelList.addAll(storyModels);
        notifyDataSetChanged();
    }
}
