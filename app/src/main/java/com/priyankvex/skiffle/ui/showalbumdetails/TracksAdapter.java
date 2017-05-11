package com.priyankvex.skiffle.ui.showalbumdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.model.AlbumDetails;
import com.priyankvex.skiffle.model.TrackItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by @priyankvex on 6/4/17.
 */

class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.ViewHolder>{

    private Context mContext;

    private ShowAlbumDetailsMvp.ShowAlbumDetailsView mCommunicator;

    private ArrayList<TrackItem> mTracks;

    private AlbumDetails mAlbum;

    @Inject
    TracksAdapter(ShowAlbumDetailsMvp.ShowAlbumDetailsView communicator, Context context){
        this.mContext = context;
        this.mCommunicator = communicator;
        this.mTracks = new ArrayList<>();
        //Log.d("owlcity", "New Adapter created");
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
                int position = viewHolder.getAdapterPosition();
                mCommunicator.onAlbumTrackClicked(mTracks.get(position), position);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrackItem item = mTracks.get(position);
        holder.textViewTitle.setText(item.name);
        holder.textViewArtist.setText(mAlbum.artists.get(0).name);
        holder.textViewAlbum.setText(mAlbum.name);
        Picasso.with(mContext)
                .load(mAlbum.images.get(2).url)
                .into(holder.imageViewCover);
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }

    void swapData(AlbumDetails album){
        this.mAlbum = album;
        mTracks.clear();
        mTracks.addAll(album.tracks.items);
        //Log.d("owlcity", "No of songs " + mTracks.size());
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
