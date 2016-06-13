package opensource.popularmoviesstage1.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import opensource.popularmoviesstage1.R;
import opensource.popularmoviesstage1.data.model.Reviews;

/**
 * Created by Rajan Maurya on 12/6/16.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Reviews mReviews;

    public ReviewsAdapter(Context context, Reviews results) {
        mReviews = results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_review, parent, false);

        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder) {
            String author="By :-"+ mReviews.getResults().get(position).getAuthor();
            ((ViewHolder) holder).author.setText(author);
            ((ViewHolder) holder).content.setText(mReviews.getResults().get(position).getContent());

        }
    }

    @Override
    public int getItemCount() {
        return mReviews.getTotal_results();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvAuthor)
        TextView author;

        @BindView(R.id.tvContent)
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
