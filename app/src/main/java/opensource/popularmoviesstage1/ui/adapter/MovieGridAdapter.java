package opensource.popularmoviesstage1.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.squareup.picasso.Picasso;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.model.MovieResult;

public class MovieGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieResult> mResults;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;


    public MovieGridAdapter(Context context, List<MovieResult> results) {
        mResults = results;
    }

    public void setResults(List<MovieResult> results) {
        mResults = results;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar)v.findViewById(R.id.progressBar1);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_recyclerview, parent, false);

            vh = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_progressbar, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder) {
            Picasso.with(holder.itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w500" +
                            mResults.get(position).getPosterPath())
                    .into(((ViewHolder) holder).moviePoster);
            Log.d("Poster Image Link", mResults.get(position).getPosterPath());
        }
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mResults.get(position)!=null? VIEW_ITEM: VIEW_PROG;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movieposter) ImageView moviePoster;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}