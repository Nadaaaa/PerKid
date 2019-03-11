package com.example.xdev.perkid.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xdev.perkid.R;
import com.example.xdev.perkid.models.HistoryAdapterModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private Context context;
    private List<HistoryAdapterModel> historyList;

    public HistoryAdapter(Context context, List<HistoryAdapterModel> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutHistoryItem = R.layout.item_history;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutHistoryItem, viewGroup, false);
        return new HistoryViewHolder(view);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, final int i) {
        if (historyList.get(i).getSocialMediaName().equals("youtube")) {
            historyViewHolder.SocialMediaName.setText(context.getString(R.string.youtube));
            historyViewHolder.SocialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_youtube));
        }

        if (historyList.get(i).getSocialMediaName().equals("facebook")) {
            historyViewHolder.SocialMediaName.setText(context.getString(R.string.facebook));
            historyViewHolder.SocialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_facebook));
        }

        if (historyList.get(i).getSocialMediaName().equals("instagram")) {
            historyViewHolder.SocialMediaName.setText(context.getString(R.string.instagram));
            historyViewHolder.SocialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_instagram));
        }

        if (historyList.get(i).getSocialMediaName().equals("pinterest")) {
            historyViewHolder.SocialMediaName.setText(context.getString(R.string.pinterest));
            historyViewHolder.SocialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_pinterest));

        }
        if (historyList.get(i).getSocialMediaName().equals("snapchat")) {
            historyViewHolder.SocialMediaName.setText(context.getString(R.string.snapchat));
            historyViewHolder.SocialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_snapchat));
        }

        if (historyList.get(i).getSocialMediaName().equals("twitter")) {
            historyViewHolder.SocialMediaName.setText(context.getString(R.string.twitter));
            historyViewHolder.SocialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_twitter));
        }

        historyViewHolder.SocialMediaTimeAndDate.setText(String.valueOf(historyList.get(i).getSocialMediaTimeAndData()));
        historyViewHolder.kidLocation.setText(historyList.get(i).getKidLocation());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView SocialMediaName;
        TextView SocialMediaTimeAndDate;
        ImageView SocialMediaImage;
        TextView kidLocation;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            SocialMediaImage = itemView.findViewById(R.id.history_image);
            SocialMediaName = itemView.findViewById(R.id.history_name);
            SocialMediaTimeAndDate = itemView.findViewById(R.id.history_timeAndDate);
            kidLocation = itemView.findViewById(R.id.history_kidLocation);
        }
    }
}
