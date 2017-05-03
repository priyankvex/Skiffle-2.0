package com.priyankvex.skiffle.ui.showartistdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.model.AlbumDetails;
import com.priyankvex.skiffle.model.ArtistTopTracks;
import com.priyankvex.skiffle.model.TrackItem;
import com.priyankvex.skiffle.model.TrackList;
import com.priyankvex.skiffle.ui.showalbumdetails.ShowAlbumDetailsMvp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by priyankvex on 3/5/17.
 */

class ArtistTracksAdapter extends RecyclerView.Adapter<ArtistTracksAdapter.ViewHolder>{

    private Context mContext;

    private ShowArtistDetailsMvp.ShowArtistDetailsView mCommunicator;

    private ArrayList<TrackItem> mTracks;


    @Inject
    ArtistTracksAdapter(ShowArtistDetailsMvp.ShowArtistDetailsView communicator, Context context){
        this.mContext = context;
        this.mCommunicator = communicator;
        this.mTracks = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.list_item_track, parent, false);
        final ViewHolder viewHolder = new ViewHolder(contactView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrackItem item = mTracks.get(position);
        holder.textViewTitle.setText(item.name);
        holder.textViewArtist.setText(item.artists.get(0).name);
        holder.textViewAlbum.setText(item.album.name);
        if (item.album.images.size() >= 3){
            Picasso.with(mContext)
                    .load(item.album.images.get(2).url)
                    .placeholder(R.color.colorPrimaryLight)
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

    void swapData(ArrayList<TrackItem> tracks){
        mTracks.clear();
        mTracks.addAll(tracks);
        Log.d("owlcity", "No of songs " + mTracks.size());
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewArtist;
        private TextView textViewAlbum;
        private ImageView imageViewCover;

        ViewHolder(View itemView){
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.text_view_title);
            imageViewCover = (ImageView) itemView.findViewById(R.id.image_view_cover);
            textViewArtist = (TextView) itemView.findViewById(R.id.text_view_artist);
            textViewAlbum = (TextView) itemView.findViewById(R.id.text_view_album);
        }

    }
}
