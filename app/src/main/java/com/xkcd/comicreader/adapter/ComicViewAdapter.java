package com.xkcd.comicreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xkcd.comicreader.R;

import java.util.ArrayList;
import java.util.List;

public class ComicViewAdapter extends RecyclerView.Adapter<ComicViewAdapter.ItemHolder> {

    private LayoutInflater mInflater;
    private List<String> mDatas;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public ComicViewAdapter(Context context, List<String> dataList){  // , List<String> datas

        this.mInflater=LayoutInflater.from(context);
        this.mDatas =dataList;
        this.mContext = context;
    }

    @Override
    public ComicViewAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create item recall func
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicViewAdapter.ItemHolder holder, int position) {
        // bind data
        String itemTitle = mDatas.get(position);
        // set data
        holder.setData(itemTitle, position);


    }

    @Override
    public int getItemViewType(int position) {
        // avoid items out of order
        return position;
    }

    @Override
    public int getItemCount() {
        // return number of items
        if(mDatas != null){
            return mDatas.size();
        }
        return 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        private TextView mText;
        private  int mPosition;

        private ItemHolder(View itemView){

            super(itemView);
            mText = itemView.findViewById(R.id.text);

            // set item onClick listener
            clickEvent(itemView);

        }

        private void setData(String data, int position){
            mText.setText(data);
            this.mPosition = position;
        }

        private void clickEvent(View itemView){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(mPosition, view);
                }
            });
        }
    }

    // create interface for call OnClick outside
    public interface OnItemClickListener{
        void onItemClick(int position, View view);
    }

    // function could be used outside
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }


    public void addItem(ArrayList<String> newDatas) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
        this.mDatas.addAll(newDatas);

        notifyDataSetChanged();
    }



}
