package com.onwhichtvchannel.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onwhichtvchannel.Model.DataModel;
import com.onwhichtvchannel.R;

import java.util.ArrayList;

/**
 * Created by vinove on 3/10/16.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<DataModel> dataModelArrayList;
    private Typeface latoBlack;

    public DataAdapter(Context context, ArrayList<DataModel> dataModelArrayList) {
        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
        latoBlack = Typeface.createFromAsset(context.getAssets(), "Lato-Black.ttf");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.data_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataModel model = dataModelArrayList.get(position);
        holder.header.setText(model.getHeader());
        holder.header.setTypeface(latoBlack);
        ItemAdapter itemAdapter = new ItemAdapter(context, model.getItemModelArrayList());
        holder.itemList.setAdapter(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView header;
        RecyclerView itemList;
        RecyclerView.LayoutManager mLayoutManager;

        public MyViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.section);
            itemList = (RecyclerView) itemView.findViewById(R.id.item_list);
            mLayoutManager = new LinearLayoutManager(context);
            itemList.setLayoutManager(mLayoutManager);
        }
    }

}
