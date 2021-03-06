package com.priyankvex.skiffle.ui.favorites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.model.TrackItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by priyankvex on 9/4/17.
 */

class FavoriteTracksAdapter extends RecyclerView.Adapter<FavoriteTracksAdapter.ViewHolder>{

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewArtist;
        private TextView textViewType;
        private ImageView imageViewCover;

        ViewHolder(View itemView){
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.text_view_title);
            imageViewCover = (ImageView) itemView.findViewById(R.id.image_view_cover);
            textViewArtist = (TextView) itemView.findViewById(R.id.text_view_artist);
            textViewType = (TextView) itemView.findViewById(R.id.text_view_type);
        }

    }

    private Context mContext;

    private FavoritesMvp.FavoritesView mCommunicator;

    private ArrayList<TrackItem> mTracks;

    @Inject
    FavoriteTracksAdapter(FavoritesMvp.FavoritesView communicator, Context context){
        this.mContext = context;
        this.mCommunicator = communicator;
        this.mTracks = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.list_item_new_releases, parent, false);
        final ViewHolder viewHolder = new ViewHolder(contactView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                mCommunicator.onFavoriteTrackItemClicked(position, mTracks.get(position));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrackItem item = mTracks.get(position);
        holder.textViewTitle.setText(item.name);
        holder.textViewArtist.setText(item.artists.get(0).name);
        holder.textViewType.setText(item.type);
        if (item.album.images.size() >= 3){
            Picasso.with(mContext)
                    .load(item.album.images.get(2).url)
                    .into(holder.imageViewCover);
        }
        else{
            Picasso.with(mContext)
                    .load(R.color.colorPrimaryLight)
                    .into(holder.imageViewCover);
        }
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    void swapData(ArrayList<TrackItem> items){
        mTracks.clear();
        mTracks.addAll(items);
        notifyDataSetChanged();
    }

}
