package com.priyankvex.skiffle.ui.showtrackdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.priyankvex.skiffle.R;
import com.priyankvex.skiffle.SkiffleApp;
import com.priyankvex.skiffle.component.DaggerShowTrackDetailsComponent;
import com.priyankvex.skiffle.component.ShowTrackDetailsComponent;
import com.priyankvex.skiffle.model.TrackDetails;
import com.priyankvex.skiffle.module.ShowTrackDetailsModule;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by priyankvex on 4/5/17.
 */

public class ShowTrackDetailsActivity extends AppCompatActivity implements ShowTrackDetailsMvp.ShowTrackDetailsView{

    @Inject
    ShowTrackDetailsPresenter mPresenter;

    @BindView(R.id.image_view_album_cover)
    ImageView imageViewAlbumCover;

    @BindView(R.id.text_view_artist)
    TextView textViewArtist;

    @BindView(R.id.text_view_track_name)
    TextView textViewTrackName;

    @BindView(R.id.text_view_album)
    TextView textViewAlbum;

    @BindView(R.id.text_view_type)
    TextView textViewType;

    @BindView(R.id.button_like)
    LikeButton buttonLike;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.button_play_sample)
    Button buttonPlaySample;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_track_details);
        ButterKnife.bind(this);
        ShowTrackDetailsComponent component = DaggerShowTrackDetailsComponent.builder()
                .showTrackDetailsModule(new ShowTrackDetailsModule(this))
                .skiffleApplicationComponent(SkiffleApp.getInstance().getComponent())
                .build();
        component.inject(this);
        setUpToolbar();
        mPresenter.setSavedStatus(getIntent().getBooleanExtra("is_saved", false));
        mPresenter.loadTrackDetails(getIntent().getStringExtra("track_id"));
        buttonLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                buttonLike.setEnabled(false);
                mPresenter.saveTrackToFavorites();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                buttonLike.setEnabled(false);
                mPresenter.deleteTrackFromFavorites();
            }
        });
    }

    @Override
    public void showTrackDetails(TrackDetails trackDetails) {
        progressBar.setVisibility(View.GONE);
        textViewTrackName.setVisibility(View.VISIBLE);
        textViewAlbum.setVisibility(View.VISIBLE);
        textViewArtist.setVisibility(View.VISIBLE);
        textViewType.setVisibility(View.VISIBLE);
        buttonLike.setVisibility(View.VISIBLE);
        imageViewAlbumCover.setVisibility(View.VISIBLE);
        buttonPlaySample.setVisibility(View.VISIBLE);
        textViewTrackName.setText(trackDetails.name);
        if (trackDetails.artists.size() != 0){
            textViewArtist.setText(trackDetails.artists.get(0).name);
        }
        textViewType.setText(trackDetails.type);
        textViewAlbum.setText(trackDetails.album.name);
        if (trackDetails.album.images.size() != 0){
            Picasso.with(this)
                    .load(trackDetails.album.images.get(0).url)
                    .placeholder(R.color.colorPrimaryLight)
                    .into(imageViewAlbumCover);
        }

    }

    @Override
    public void showErrorUi() {

    }

    @Override
    public void toggleLikeButton(boolean status) {
        buttonLike.setLiked(status);
    }

    @Override
    public void reneableLikeButton() {
        buttonLike.setEnabled(true);
    }

    private void setUpToolbar(){
        toolbar.setTitle(getIntent().getStringExtra("track_title"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
