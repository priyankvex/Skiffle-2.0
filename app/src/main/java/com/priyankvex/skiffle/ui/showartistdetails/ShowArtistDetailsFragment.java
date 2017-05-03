package com.priyankvex.skiffle.ui.showartistdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.model.ArtistDetails;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 3/5/17.
 */

public class ShowArtistDetailsFragment extends Fragment{

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.image_view_album_cover)
    ImageView imageViewAlbumCover;

    @BindView(R.id.text_view_artist_name)
    TextView textViewArtistName;

    @BindView(R.id.text_view_type)
    TextView textViewType;

    @BindView(R.id.text_view_genres)
    TextView textViewGenres;

    @BindView(R.id.details_layout)
    LinearLayout layoutDetails;

    public static ShowArtistDetailsFragment getInstance(){
        return new ShowArtistDetailsFragment();
    }

    private ShowArtistDetailsFragmentCommunicator mCommunicator;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShowArtistDetailsActivity){
            mCommunicator = (ShowArtistDetailsFragmentCommunicator) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_artist_details, container, false);
        ButterKnife.bind(this, rootView);
        mCommunicator.getArtistDetails();
        return rootView;
    }

    public void showArtistDetails(ArtistDetails artistDetails){
        progressBar.setVisibility(View.GONE);
        layoutDetails.setVisibility(View.VISIBLE);
        if (artistDetails.images.size() > 0){
            Picasso.with(getContext())
                    .load(artistDetails.images.get(0).url)
                    .into(imageViewAlbumCover);
        }
        textViewArtistName.setText(artistDetails.name);
        textViewType.setText(artistDetails.type);
        StringBuilder genres = new StringBuilder();
        for (String genre : artistDetails.genres){
            genres.append(genre);
            genres.append(" ");
        }
        textViewGenres.setText(genres);
    }

    public void showErrorUi(){

    }

    interface ShowArtistDetailsFragmentCommunicator{
        void getArtistDetails();
    }
}
