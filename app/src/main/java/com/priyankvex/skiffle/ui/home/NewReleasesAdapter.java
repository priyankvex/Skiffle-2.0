package com.priyankvex.skiffle.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.model.NewRelease;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by @priyankvex on 26/3/17.
 */

class NewReleasesAdapter extends RecyclerView.Adapter<NewReleasesAdapter.ViewHolder>{

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewArtist;
        private TextView textViewType;
        private ImageView imageViewCover;

        public ViewHolder(View itemView){
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.text_view_title);
            imageViewCover = (ImageView) itemView.findViewById(R.id.image_view_cover);
            textViewArtist = (TextView) itemView.findViewById(R.id.text_view_artist);
            textViewType = (TextView) itemView.findViewById(R.id.text_view_type);
        }

    }

    private Context mContext;

    private NewReleasesMvp.NewReleasesView mCommunicator;

    private ArrayList<NewRelease.Album.Item> newReleaseItems;

    @Inject
    NewReleasesAdapter(NewReleasesMvp.NewReleasesView communicator, Context context){
        this.mContext = context;
        this.mCommunicator = communicator;
        this.newReleaseItems = new ArrayList<>();
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
                mCommunicator.newReleaseItemClicked(position, newReleaseItems.get(position));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewRelease.Album.Item item = newReleaseItems.get(position);
        holder.textViewTitle.setText(item.name);
        holder.textViewArtist.setText(item.artists.get(0).name);
        holder.textViewType.setText(item.type);
        Picasso.with(mContext)
                .load(item.images.get(2).url)
                .into(holder.imageViewCover);
    }

    @Override
    public int getItemCount() {
        return newReleaseItems.size();
    }

    void swapData(ArrayList<NewRelease.Album.Item> items){
        newReleaseItems.clear();
        newReleaseItems.addAll(items);
        notifyDataSetChanged();
    }

}
