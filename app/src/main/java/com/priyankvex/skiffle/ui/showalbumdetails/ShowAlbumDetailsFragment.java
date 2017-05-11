package com.priyankvex.skiffle.ui.showalbumdetails;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.model.AlbumDetails;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by @priyankvex on 28/3/17.
 */

public class ShowAlbumDetailsFragment extends Fragment {

    private AlbumDetailsCommunicator mCommunicator;

    static ShowAlbumDetailsFragment getInstance(){
        return new ShowAlbumDetailsFragment();
    }

    @BindView(R.id.image_view_album_cover)
    ImageView imageViewAlbumCover;

    @BindView(R.id.text_view_album_name)
    TextView textViewAlbumName;
    @BindView(R.id.text_view_artist)
    TextView textViewArtist;
    @BindView(R.id.text_view_label)
    TextView textViewLabel;
    @BindView(R.id.text_view_album_type)
    TextView textViewAlbumType;
    @BindView(R.id.text_view_release_date)
    TextView textViewReleaseDate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ShowAlbumDetailsActivity){
            mCommunicator = (AlbumDetailsCommunicator) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_album_details, container, false);
        ButterKnife.bind(this, rootView);
        Log.d("owlcity", "Done creating album details fragment");
        mCommunicator.getAlbumDetails(this);
        return rootView;
    }

    void showErrorUi(){

    }

    void showAlbumDetails(AlbumDetails album){
        Log.d("owlcity", "Showing album details");
        Picasso.with(getContext())
                .load(album.images.get(0).url)
                .into(imageViewAlbumCover);
        textViewAlbumName.setText(album.name);
        textViewArtist.setText(album.artists.get(0).name);
        textViewReleaseDate.setText(album.releaseDate);
        textViewLabel.setText(album.label);
        textViewAlbumType.setText(album.albumType);
    }

    interface AlbumDetailsCommunicator {
        void getAlbumDetails(ShowAlbumDetailsFragment fragment);
    }
}
