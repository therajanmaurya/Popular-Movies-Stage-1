package opensource.popularmoviesstage1.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.model.Result;

public class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.RibotViewHolder> {

    private List<Result> mResults;


    public MovieGridAdapter() {
        mResults = new ArrayList<>();
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

    @Override
    public RibotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);
        return new RibotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RibotViewHolder holder, int position) {
        Result result = mResults.get(position);

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    class RibotViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movieposter) ImageView moviePoster;;

        public RibotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}