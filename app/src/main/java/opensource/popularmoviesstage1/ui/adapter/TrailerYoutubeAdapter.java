package opensource.popularmoviesstage1.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.model.Trailers;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class TrailerYoutubeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Trailers mTrailers;
    private Context mContext;


    public TrailerYoutubeAdapter(Context context, Trailers trailers) {
        mTrailers = trailers;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_detail_trailers, parent, false);
        vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).mTitle.setText(
                    mTrailers.getResults()
                            .get(position)
                            .getName());

            final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener
                    = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                @Override
                public void onThumbnailLoaded(
                        YouTubeThumbnailView youTubeThumbnailView, String s) {

                    youTubeThumbnailView.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).mRelativeLayoutOverYouTubeThumbnailView
                            .setVisibility(View.VISIBLE);
                }

                @Override
                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView,
                                             YouTubeThumbnailLoader.ErrorReason errorReason) {

                }
            };

            ((ViewHolder) holder).youTubeThumbnailView.initialize(
                    mContext.getString(R.string.google_api_key), new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView,
                                                    YouTubeThumbnailLoader youTubeThumbnailLoader) {

                    youTubeThumbnailLoader.setVideo(mTrailers.getResults().get(position).getKey());
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView,
                                                    YouTubeInitializationResult youTubeInitializationResult) {
                    //write something for failure
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTrailers.getResults().size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_titletrailer)
        TextView mTitle;

        @BindView(R.id.btnYoutube_player)
        ImageView mPlayButton;

        @BindView(R.id.relativeLayout_over_youtube_thumbnail)
        RelativeLayout mRelativeLayoutOverYouTubeThumbnailView;

        @BindView(R.id.youtube_thumbnail)
        YouTubeThumbnailView youTubeThumbnailView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mPlayButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) mContext, mContext
                    .getResources().getString(R.string.google_api_key),
                    mTrailers.getResults().get(getLayoutPosition()).getKey(), 100, true, false);
            mContext.startActivity(intent);
        }
    }
}

