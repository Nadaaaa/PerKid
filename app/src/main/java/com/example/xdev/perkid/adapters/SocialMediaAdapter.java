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
import com.example.xdev.perkid.models.SocialMediaAdapterModel;

import java.util.List;

public class SocialMediaAdapter extends RecyclerView.Adapter<SocialMediaAdapter.SocialMediaViewHolder> {

    private Context context;
    private List<SocialMediaAdapterModel> socialMediaList;
    private final ListItemClickListener mClickListener;

    public interface ListItemClickListener {
        void onListItemClicked(int clickedItemIndex);
    }

    public SocialMediaAdapter(Context context, List<SocialMediaAdapterModel> socialMediaList, ListItemClickListener listener) {
        this.context = context;
        this.socialMediaList = socialMediaList;
        mClickListener = listener;
    }

    @NonNull
    @Override
    public SocialMediaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutSocialMediaItem = R.layout.item_button;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutSocialMediaItem, viewGroup, false);
        return new SocialMediaViewHolder(view);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull SocialMediaViewHolder socialMediaViewHolder, final int i) {
        if (socialMediaList.get(i).getName().equals("youtube"))
            socialMediaViewHolder.socialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_youtube));

        if (socialMediaList.get(i).getName().equals("facebook"))
            socialMediaViewHolder.socialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_facebook));

        if (socialMediaList.get(i).getName().equals("instagram"))
            socialMediaViewHolder.socialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_instagram));

        if (socialMediaList.get(i).getName().equals("pinterest"))
            socialMediaViewHolder.socialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_pinterest));

        if (socialMediaList.get(i).getName().equals("snapchat"))
            socialMediaViewHolder.socialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_snapchat));

        if (socialMediaList.get(i).getName().equals("twitter"))
            socialMediaViewHolder.socialMediaImage.setImageDrawable(context.getDrawable(R.drawable.ic_twitter));

        socialMediaViewHolder.socialMediaVisitTimes.setText(String.valueOf(socialMediaList.get(i).getVisitTimes()));
    }

    @Override
    public int getItemCount() {
        return socialMediaList.size();
    }

    class SocialMediaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView socialMediaVisitTimes;
        ImageView socialMediaImage;

        SocialMediaViewHolder(@NonNull View itemView) {
            super(itemView);

            socialMediaVisitTimes = itemView.findViewById(R.id.socialMediaButton_visitTimes);
            socialMediaImage = itemView.findViewById(R.id.socialMediaButton_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onListItemClicked(getAdapterPosition());
        }
    }
}
