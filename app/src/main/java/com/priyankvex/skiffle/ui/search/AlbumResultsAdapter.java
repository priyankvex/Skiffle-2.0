package com.priyankvex.skiffle.ui.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.model.AlbumItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by priyankvex on 3/5/17.
 */

class AlbumResultsAdapter extends RecyclerView.Adapter<AlbumResultsAdapter.ViewHolder>{

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

    private SearchMvp.SearchView mCommunicator;

    private ArrayList<AlbumItem> mAlbums;

    AlbumResultsAdapter(SearchMvp.SearchView communicator, Context context){
        this.mContext = context;
        this.mCommunicator = communicator;
        this.mAlbums = new ArrayList<>();
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
                mCommunicator.onAlbumItemClicked(mAlbums.get(position),position);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AlbumItem item = mAlbums.get(position);
        holder.textViewTitle.setText(item.name);
        holder.textViewArtist.setText(item.artists.get(0).name);
        holder.textViewType.setText(item.type);
        Picasso.with(mContext)
                .load(item.images.get(2).url)
                .into(holder.imageViewCover);
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    void swapData(ArrayList<AlbumItem> items){
        mAlbums.clear();
        mAlbums.addAll(items);
        notifyDataSetChanged();
    }

}
