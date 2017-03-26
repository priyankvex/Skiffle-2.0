package com.priyankvex.skiffle.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        private TextView textView;

        public ViewHolder(View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view_title);
        }
    }

    private Picasso mPicasso;

    private ArrayList<NewRelease.Album.Item> newReleaseItems;

    @Inject
    NewReleasesAdapter(Picasso picasso){
        this.mPicasso = picasso;
        this.newReleaseItems = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.list_item_new_releases, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewRelease.Album.Item item = newReleaseItems.get(position);
        holder.textView.setText(item.name);
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
