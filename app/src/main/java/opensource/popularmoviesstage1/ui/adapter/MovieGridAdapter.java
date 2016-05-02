package opensource.popularmoviesstage1.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.model.Result;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieViewHolder> {

    private List<Result> mResults;


    public MovieGridAdapter() {
        mResults = new ArrayList<>();
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Picasso.with(holder.itemView.getContext())
                .load("http://image.tmdb.org/t/p/w500" + mResults.get(position).getPosterPath())
                .into(holder.moviePoster);
        Log.d("Poster Image Link" , mResults.get(position).getPosterPath());
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movieposter) ImageView moviePoster;;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}