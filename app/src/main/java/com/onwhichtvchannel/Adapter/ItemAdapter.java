package com.onwhichtvchannel.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.onwhichtvchannel.Model.ItemModel;
import com.onwhichtvchannel.R;

import java.util.ArrayList;

/**
 * Created by vinove on 7/11/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ItemModel> itmeModelArrayList;
    private Typeface latoBold, latoBlack, latoRegular;

    public ItemAdapter(Context context, ArrayList<ItemModel> itmeModelArrayList) {
        this.context = context;
        this.itmeModelArrayList = itmeModelArrayList;
        latoBold = Typeface.createFromAsset(context.getAssets(), "Lato-Bold.ttf");
        latoBlack = Typeface.createFromAsset(context.getAssets(), "Lato-Black.ttf");
        latoRegular= Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ItemModel model = itmeModelArrayList.get(position);
        holder.time.setText(model.getTime());

        holder.time.setTypeface(latoRegular);
        holder.name.setTypeface(latoBold);

      //  holder.category.setText(model.getCategory());
        holder.name.setText(model.getCategory());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

//        .showImageForEmptyUri(R.drawable.update_profile)
//                .showImageOnFail(R.drawable.update_profile)
//                .showImageOnLoading(R.drawable.update_profile)

        imageLoader.displayImage(model.getCategory_icon(), holder.categoryIcon, options);
        imageLoader.displayImage(model.getChannel_icon(), holder.channelIcon, options);
    }


    @Override
    public int getItemCount() {
        return itmeModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dateHeader, time, name, category;
        public ImageView categoryIcon, channelIcon;
        LinearLayout header;

        public MyViewHolder(View itemView) {
            super(itemView);
            dateHeader = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.tv_time);
         //   category = (TextView) itemView.findViewById(R.id.tv_notes);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            categoryIcon= (ImageView) itemView.findViewById(R.id.iv_category_icon);
            channelIcon = (ImageView) itemView.findViewById(R.id.iv_channel_icon);
        }
    }

}
