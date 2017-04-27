package com.priyankvex.skiffle.ui.search;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.model.SearchResultsListItem;
import com.priyankvex.skiffle.model.TrackItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by priyankvex on 18/4/17.
 */

public class PreviewSearchResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<SearchResultsListItem> data;

    private Context mContext;

    private SearchMvp.SearchView mCommunicator;

    @Inject
    public PreviewSearchResultsAdapter(SearchMvp.SearchView communicator, Context context) {
        this.data = new ArrayList<>();
        this.mContext = context;
        this.mCommunicator = communicator;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final RecyclerView.ViewHolder viewHolder;

        if (viewType == TYPE_ITEM) {
            //inflate your layout and pass it to view holder
            View v1 = inflater.inflate(R.layout.list_item_new_releases, parent, false);
            viewHolder = new ListItemViewHolder(v1);
        }
        else if (viewType == TYPE_HEADER) {
            //inflate your layout and pass it to view holder
            View v1 = inflater.inflate(R.layout.list_header_search_results, parent, false);
            viewHolder = new HeaderViewHolder(v1);
            v1.findViewById(R.id.button_view_more).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    mCommunicator.onSearchResultsPreviewItemClicked(data.get(position), position);
                }
            });
        }
        else {
            viewHolder = null;
        }

        if (viewHolder == null){
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                SearchResultsListItem item = data.get(position);
                if (item.viewType == SearchResultsListItem.ViewType.ITEM_VIEW){
                    mCommunicator.onSearchResultsPreviewItemClicked(data.get(position), position);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchResultsListItem item = data.get(position);
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
            viewHolder.textViewTitle.setText(item.title);
        } else if (holder instanceof ListItemViewHolder) {
            ListItemViewHolder viewHolder = (ListItemViewHolder) holder;
            viewHolder.textViewTitle.setText(item.title);
            viewHolder.textViewSubtitle.setText(item.subTitle);
            viewHolder.textViewType.setText(String.valueOf(item.itemType));
            Picasso.with(mContext).load(item.thumbImageUrl)
                    .placeholder(R.color.colorPrimaryLight)
                    .into(viewHolder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).viewType == SearchResultsListItem.ViewType.HEADER_VIEW){
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewSubtitle;
        TextView textViewType;
        ImageView imageView;

        ListItemViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView)itemView.findViewById(R.id.text_view_title);
            textViewSubtitle = (TextView) itemView.findViewById(R.id.text_view_artist);
            textViewType = (TextView) itemView.findViewById(R.id.text_view_type);
            imageView = (ImageView) itemView.findViewById(R.id.image_view_cover);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewViewMore;
        TextView textViewTitle;

        HeaderViewHolder(View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.text_view_header_title);
            textViewViewMore = (TextView) itemView.findViewById(R.id.button_view_more);

        }
    }

    void swapData(ArrayList<SearchResultsListItem> results){
        data.clear();
        data.addAll(results);
        notifyDataSetChanged();
    }
}
